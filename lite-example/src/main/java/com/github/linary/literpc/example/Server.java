package com.github.linary.literpc.example;

import com.github.linary.literpc.server.RpcServer;

public class Server {

    public static void main(String[] args) {
        RpcServer server = new RpcServer();
        server.register(CalcService.class, new CalcServiceImpl());
        server.start();
    }
}
