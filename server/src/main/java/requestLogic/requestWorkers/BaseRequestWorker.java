package requestLogic.requestWorkers;

import requestLogic.requests.ServerRequest;

public class BaseRequestWorker implements RequestWorker {


    @Override
    public void workWithRequest(ServerRequest request) {
        System.out.println("we've got base request wow");
    }
}
