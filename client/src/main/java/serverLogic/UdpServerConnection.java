package serverLogic;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.concurrent.*;

public class UdpServerConnection implements ServerConnection {
    public static final int BUFFER_SIZE = 4096;

    private final ExecutorService service;
    protected final DatagramChannel channel;
    protected SocketAddress address;

    {
        service = Executors.newCachedThreadPool();
    }

    protected UdpServerConnection(DatagramChannel channel, SocketAddress address) {
        this.channel = channel;
        this.address = address;
    }


    /**
     * Method for open a connection.
     */
    @Override
    public void openConnection() throws IOException {
        if (!channel.isConnected()) {
            channel.connect(address);
            System.out.println("Opened channel to: " + address);
        }
    }

    /**
     * Method for close a connection
     */
    @Override
    public void closeConnection() throws IOException {
        if (channel.isConnected() && channel.isOpen()) {
            try {
                channel.disconnect();
                channel.close();
            } catch (IOException e) {
                channel.close();
            }
        }
    }

    Future<ByteArrayInputStream> bosFuture;
    boolean lastRequestSuccess = true;

    @Override
    public ByteArrayInputStream sendData(byte[] bytesToSend) throws IOException {
        ByteArrayInputStream bos = null;
        if (channel.isConnected() && channel.isOpen()) {
            var buf = ByteBuffer.wrap(bytesToSend);
            channel.send(buf, address);

            if (lastRequestSuccess) {
                Callable<ByteArrayInputStream> callable = this::listenServer;
                bosFuture = service.submit(callable);
            }

            try {
                bos = bosFuture.get(5, TimeUnit.SECONDS);
                lastRequestSuccess = true;
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            } catch (ExecutionException e) {
                lastRequestSuccess = true;
                System.out.println("Something went wrong during execution");
                throw (IOException) e.getCause();
            } catch (TimeoutException e) {
                lastRequestSuccess = false;
                System.out.println("Time limit exceed for getting response");
                throw new PortUnreachableException("Server may be unavailable");
            }
        } else this.openConnection();
        return bos;
    }

    public ByteArrayInputStream listenServer() throws IOException {
        ByteArrayInputStream res = null;
        if (channel.isConnected() && channel.isOpen()) {
            ByteBuffer buf = ByteBuffer.allocate(BUFFER_SIZE);
            address = channel.receive(buf);
            System.out.println("response read");
            System.out.println("bytes: " + Arrays.toString(buf.array()));
            res = new ByteArrayInputStream(buf.array());
        } else this.openConnection();
        return res;
    }
}
