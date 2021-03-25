package com.geekbrains.server;

public class MainServer {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        new Server ( );
    }
}
