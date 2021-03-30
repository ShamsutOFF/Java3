package com.geekbrains.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class ClientHandler {
    private String nickname;
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public String getNickname() {
        return nickname;
    }

    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream (socket.getInputStream ( ));
            this.out = new DataOutputStream (socket.getOutputStream ( ));
            new Thread (() -> {
                try {
                    while (true) {
                        String msg = in.readUTF ( );
                        if (msg.startsWith ("/auth ")) {
                            String[] tokens = msg.split ("\\s");
                            String nick = server.getAuthService ( ).getNicknameByLoginAndPassword (tokens[1], tokens[2]);
                            if (nick != null && !server.isNickBusy (nick)) {
                                sendMsg ("/authok " + nick);
                                nickname = nick;
                                server.subscribe (this);
                                server.serviceMsg (nickname + " подключился");
                                break;
                            }
                        }
                    }
                    while (true) {
                        String msg = in.readUTF ( );
                        if (msg.startsWith ("/")) {
                            if (msg.equals ("/end")) {
                                server.serviceMsg (nickname + " отключился");
                                sendMsg ("/end");
                                break;
                            }
                            if (msg.startsWith ("/w ")) {
                                String[] tokens = msg.split ("\\s", 3);
                                server.privateMsg (this, tokens[1], tokens[2]);
                            }
                            if (msg.startsWith ("/upNick")) {
                                String[] tokens = msg.split ("\\s", 2);
//                                server.serviceMsg (Arrays.toString (tokens));
                                if (tokens.length == 2 && !tokens[1].trim ( ).equals (" ")) {
                                    String newNickname = tokens[1].trim ( );
//                                    server.serviceMsg (newNickname);

                                    if (!server.isNickBusy (newNickname) && server.getAuthService ( ).updateNickname (getNickname ( ), newNickname)) {
                                        String message = String.format ("Никнейм пользователя \"%s\" был заменен на \"%s\"", getNickname ( ), newNickname);
                                        server.broadcastMsg (message);

                                        nickname = newNickname;
                                        server.broadcastClientsList ( );
                                    }
                                }
                            }
                        } else {
                            server.broadcastMsg (nickname + ": " + msg);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace ( );
                } finally {
                    ClientHandler.this.disconnect ( );
                }
            }).start ( );
        } catch (IOException e) {
            e.printStackTrace ( );
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF (msg);
        } catch (IOException e) {
            e.printStackTrace ( );
        }
    }

    public void disconnect() {
        server.unsubscribe (this);
        try {
            in.close ( );
        } catch (IOException e) {
            e.printStackTrace ( );
        }
        try {
            out.close ( );
        } catch (IOException e) {
            e.printStackTrace ( );
        }
        try {
            socket.close ( );
        } catch (IOException e) {
            e.printStackTrace ( );
        }
    }
}
