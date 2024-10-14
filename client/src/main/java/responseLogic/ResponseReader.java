package responseLogic;

import exceptions.GotAnErrorResponseException;
import exceptions.ProceedException;
import responses.BaseResponse;
import responses.ErrorResponse;
import responses.IntermediateResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class ResponseReader {
    final InputStream in;

    public ResponseReader(InputStream in) {
        this.in = in;
    }

    public BaseResponse readObject() throws IOException, ClassNotFoundException, GotAnErrorResponseException, ProceedException {
        ObjectInputStream ois = new ObjectInputStream(in);
        BaseResponse result = (BaseResponse) ois.readObject();
        // todo: pattern command....
        if (result instanceof IntermediateResponse response) {
            result = new IntermediateResponseHandler(response)
                    .proceedIntermediateResponse();
        }
        if (result instanceof ErrorResponse)
            throw new GotAnErrorResponseException((ErrorResponse) result);
        return result;
    }
}
