package com.geekbrains.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class Controller implements Initializable {
    @FXML
    TextArea textArea;

    @FXML
    TextField msgField, loginField;

    @FXML
    HBox msgPanel, authPanel;

    @FXML
    PasswordField passField;

    @FXML
    ListView<String> clientsList;

    private boolean authenticated;
    private String nickname;

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
        authPanel.setVisible (!authenticated);
        authPanel.setManaged (!authenticated);
        msgPanel.setVisible (authenticated);
        msgPanel.setManaged (authenticated);
        clientsList.setVisible (authenticated);
        clientsList.setManaged (authenticated);
        if (!authenticated) {
            nickname = "";
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAuthenticated (false);
        clientsList.setOnMouseClicked (event -> {
            if (event.getClickCount ( ) == 2) {
                String nickname = clientsList.getSelectionModel ( ).getSelectedItem ( );
                msgField.setText ("/w " + nickname + " ");
                msgField.requestFocus ( );
                msgField.selectEnd ( );
            }
        });
        linkCallbacks ( );
    }

    public void sendAuth() {
        Network.sendAuth (loginField.getText ( ), passField.getText ( ));
        loginField.clear ( );
        passField.clear ( );
    }

    public void sendMsg() {
        if (Network.sendMsg (msgField.getText ( ))) {
            msgField.clear ( );
            msgField.requestFocus ( );
        }
    }

    public void upNick() {
        msgField.clear ( );
        msgField.setText ("/upNick ");
        msgField.requestFocus ( );
        msgField.selectEnd ( );

    }

    public void sendExit() {
        try {
            Network.sendMsg ("/end");
        } finally {
            Platform.exit ( );
        }
    }

    public void showAlert(String msg) {
        Platform.runLater (() -> {
            Alert alert = new Alert (Alert.AlertType.WARNING, msg, ButtonType.OK);
            alert.showAndWait ( );
        });
    }

    public void linkCallbacks() {
        Network.setCallOnException (args -> showAlert (args[0].toString ( )));

        Network.setCallOnCloseConnection (args -> setAuthenticated (false));

        Network.setCallOnAuthenticated (args -> {
            setAuthenticated (true);
            nickname = args[0].toString ( );
        //    Window stage = clientsList.getScene ().getWindow ( );

          //  stage = (Stage) clientsList.getScene().getWindow();
           // stage.setTitle (nickname);
            //--------- 3 задание ---------//
            Path historyPath = Paths.get ("client/src/main/java/com/geekbrains/client/history/history_" + nickname + ".txt");
            try {
                if (Files.exists (historyPath)) {
                    List<String> listLines = Files.readAllLines (historyPath);
                    if (listLines.size ( ) > 100) {
                        listLines.subList (listLines.size ( ) - 100, listLines.size ( )).forEach (new Consumer<String> ( ) {
                            @Override
                            public void accept(String s) {
                                textArea.appendText (s + "\n");
                            }
                        });
                    } else {
                        listLines.forEach (new Consumer<String> ( ) {
                            @Override
                            public void accept(String s) {
                                textArea.appendText (s + "\n");
                            }
                        });
                    }
                }
            } catch (IOException e) {
                e.printStackTrace ( );
            }
            //-----------
        });

        Network.setCallOnMsgReceived (args -> {
            String msg = args[0].toString ( );
            if (msg.startsWith ("/")) {
                if (msg.startsWith ("/clients ")) {
                    String[] tokens = msg.split ("\\s");
                    Platform.runLater (() -> {
                        clientsList.getItems ( ).clear ( );
                        for (int i = 1; i < tokens.length; i++) {
                            clientsList.getItems ( ).add (tokens[i]);
                        }
                    });
                }
            } else {
                textArea.appendText (msg + "\n");

                //-----3 задание
                Path testFilePath = Paths.get ("client/src/main/java/com/geekbrains/client/history/history_" + nickname + ".txt");
                try {
                    String txt = msg + "\n";
                    byte[] bs = txt.getBytes ( );
                    Files.write (testFilePath, bs, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                } catch (Exception e) {
                    e.printStackTrace ( );
                }
                //-----------
            }
        });
    }
}