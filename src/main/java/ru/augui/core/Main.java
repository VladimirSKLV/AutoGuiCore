package ru.augui.core;

import py4j.GatewayServer;

public class Main {

    public static void main(String[] args) {
        Core core = new Core();
        GatewayServer server = new GatewayServer(core);
        server.start();
    }

}
