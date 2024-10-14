package responseLogic.responseSenders;

import requestLogic.CallerBack;
import responses.ByteArrayPacketResponse;
import responses.IntermediateResponse;
import serverLogic.ServerConnections;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class IntermediateResponseSender {

    private static int responseKey = 1;

    public void sendLargeResponse(ByteArrayOutputStream bos, CallerBack to, ServerConnections connection) {

        ByteBuffer buffer = ByteBuffer.wrap(bos.toByteArray());

        int deliveryCount = (int) Math.floor(buffer.capacity() / 3500.0) + 1;

        IntermediateResponse response = new IntermediateResponse(responseKey, deliveryCount);
        ResponseSender.sendSyncResponse(response, connection, to);


        for (int i = 0; i < deliveryCount; i++) {
            byte[] arrayToSend = new byte[3500];
            if (buffer.remaining() >= 3500) {
                buffer = buffer.get(arrayToSend, 0, 3500);
            } else {
                arrayToSend = new byte[buffer.remaining()];
                buffer = buffer.get(arrayToSend, 0, buffer.remaining());
            }
            ByteArrayPacketResponse packetResponse = new ByteArrayPacketResponse(responseKey, i, arrayToSend);
            ResponseSender.sendSyncResponse(packetResponse, connection, to);
        }

        responseKey++;
    }
}
