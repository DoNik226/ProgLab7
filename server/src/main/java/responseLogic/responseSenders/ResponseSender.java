package responseLogic.responseSenders;

import requestLogic.CallerBack;
import responses.BaseResponse;
import serverLogic.ServerConnections;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ResponseSender {
    private static final Executor sendRsMtExecutor = Executors.newWorkStealingPool();

    public static void sendResponse(BaseResponse response, ServerConnections connection, CallerBack to) {
        sendRsMtExecutor.execute(() -> {
            if (response != null) {
                sendResponseLogic(response, connection, to);
            }
        });
    }

    protected static void sendSyncResponse(BaseResponse response, ServerConnections connection, CallerBack to) {
        sendResponseLogic(response, connection, to);
    }

    private static void sendResponseLogic(BaseResponse response, ServerConnections connection, CallerBack to) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos;
            oos = new ObjectOutputStream(bos);
            oos.writeObject(response);
            if (bos.size() > 4096) {
                new IntermediateResponseSender().sendLargeResponse(bos, to, connection);
            } else
                connection.sendData(bos.toByteArray(), to.getAddress(), to.getPort());
            System.out.println("response sent.");
        } catch (IOException e) {
            System.out.println("Something went wrong during I/O " + e);
        }
    }
}
