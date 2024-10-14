package responseLogic;

import exceptions.GotAnErrorResponseException;
import exceptions.ProceedException;
import responses.BaseResponse;
import responses.ByteArrayPacketResponse;
import responses.IntermediateResponse;
import serverLogic.ServerConnectionHandler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class IntermediateResponseHandler {
    private final int responseKey;
    private final int totalDeliveryCount;

    public IntermediateResponseHandler(IntermediateResponse response) {
        this.responseKey = response.getResponseKey();
        this.totalDeliveryCount = response.getTotalDeliveries();
    }


    public BaseResponse proceedIntermediateResponse()
            throws IOException, GotAnErrorResponseException, ClassNotFoundException, ProceedException {

        ByteBuffer buffer = ByteBuffer.allocate(totalDeliveryCount * 5000);

        for (int i = 0; i < totalDeliveryCount; i++) {
            BaseResponse response = new ResponseReader(
                    ServerConnectionHandler.getCurrentConnection().listenServer()
            ).readObject();
            if (!response.getClass().equals(ByteArrayPacketResponse.class))
                throw new ProceedException("Got unexpected object");
            ByteArrayPacketResponse bpr = (ByteArrayPacketResponse) response;
            if (bpr.getResponseKey() != responseKey) {
                i--;
                continue;
                // packet was lost... to a far away land
            }
            if (bpr.getDeliveryNumber() != i)
                throw new ProceedException("We have lost some packets...");
            buffer.put(((ByteArrayPacketResponse) response).getPacket());
        }

        return new ResponseReader(new ByteArrayInputStream(buffer.array())).readObject();
    }
}
