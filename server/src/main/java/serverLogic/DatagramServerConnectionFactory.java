package serverLogic;

import java.net.SocketException;
public class DatagramServerConnectionFactory implements ServerConnectionFactorys {

    @Override
    public ServerConnections initializeServer(int port) {
        try {
            return new DatagramServerConnection(port);
        } catch (SocketException e) {
            System.out.println("Cannot initialize server connection! " + e);
            System.exit(-1);
        }
        return null;
    }
}
