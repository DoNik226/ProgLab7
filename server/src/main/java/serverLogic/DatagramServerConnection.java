package serverLogic;

import requestLogic.CallerBack;
import requestLogic.StatusRequest;
import requestLogic.StatusRequestBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

public class DatagramServerConnection implements ServerConnections {
    private final int BUFFER_SIZE = 4096;
    private final DatagramSocket ds;

    protected DatagramServerConnection(int port) throws SocketException {
        ds = new DatagramSocket(port);
    }

    public StatusRequest listenAndGetData() {
        byte[] buffer = new byte[BUFFER_SIZE];
        try {
            DatagramPacket dp;
            dp = new DatagramPacket(buffer, buffer.length);
            ds.receive(dp);

            System.out.println("Received connection.");
            System.out.println("Bytes: " + Arrays.toString(dp.getData()));

            return StatusRequestBuilder.initialize().setObjectStream(new ByteArrayInputStream(dp.getData())).setCallerBack(new CallerBack(dp.getAddress(), dp.getPort())).setCode(200).build();
        } catch (IOException e) {
            System.out.println("Something went wrong during I/O. " + e);
        }
        return StatusRequestBuilder.initialize().setCode(-501).build();
    }

    @Override
    public void sendData(byte[] data, InetAddress addr, int port) {
        try {
            DatagramPacket dpToSend = new DatagramPacket(data, data.length, addr, port);
            ds.send(dpToSend);
            System.out.println("data sent");
        } catch (IOException ex) {
            System.out.println("Something went wrong during I/O.");
        }
    }

}
