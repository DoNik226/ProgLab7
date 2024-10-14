package serverLogic;

import requestLogic.StatusRequest;

import java.net.InetAddress;

public interface ServerConnections {
    StatusRequest listenAndGetData();

    void sendData(byte[] data, InetAddress addr, int port);
}