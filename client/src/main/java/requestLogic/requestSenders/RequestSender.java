package requestLogic.requestSenders;

import exceptions.GotAnErrorResponseException;
import exceptions.ProceedException;
import requests.BaseRequest;
import responseLogic.ResponseReader;
import responses.BaseResponse;
import serverLogic.ServerConnection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;

public class RequestSender {

    public BaseResponse sendRequest(BaseRequest request, ServerConnection connection) throws IOException, GotAnErrorResponseException, ProceedException {
        BaseResponse response = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(request);
            System.out.println("Request sent");
            InputStream responseStream = connection.sendData(bos.toByteArray());
            if (responseStream != null) {
                ResponseReader reader = new ResponseReader(responseStream);
                response = reader.readObject();
                System.out.println("Received response");
            } else System.out.println("Received null");
        } catch (ClassNotFoundException e) {
            System.out.println("Response class not found.");
        }
        return response;
    }
}
