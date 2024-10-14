package requestLogic.requests;

import javax.annotation.Nonnull;
import requestLogic.CallerBack;
import requests.BaseRequest;
import serverLogic.ServerConnections;

public class ServerRequest {
    private final BaseRequest request;
    private final CallerBack from;
    private final ServerConnections connection;

    public ServerRequest(@Nonnull BaseRequest request, @Nonnull CallerBack from, @Nonnull ServerConnections connection) {
        this.request = request;
        this.from = from;
        this.connection = connection;
    }

    public BaseRequest getUserRequest() {
        return request;
    }

    public ServerConnections getConnection() {
        return connection;
    }

    public CallerBack getFrom() {
        return from;
    }
}

