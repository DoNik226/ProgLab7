package requestLogic.requestSenders;

import commandLogic.CommandDescription;
import exceptions.GotAnErrorResponseException;
import exceptions.ProceedException;
import requests.CommandClientRequest;
import responses.CommandStatusResponse;
import serverLogic.ServerConnection;

import java.io.IOException;
import java.net.PortUnreachableException;

public class CommandRequestSender {

    public CommandStatusResponse sendCommand(CommandDescription command, String[] args, ServerConnection connection) {
        CommandStatusResponse response = null;
        try {
            var rq = new CommandClientRequest(command, args);
            System.out.println("Sending command request...");
            response = (CommandStatusResponse) new RequestSender().sendRequest(rq, connection);
        } catch (PortUnreachableException e) {
            System.out.println("Server is unavailable. Please, wait until server will come back.");
        } catch (ProceedException e) {
            System.out.println("We've lost some packets during getting response: " + e.getMessage());
        } catch (GotAnErrorResponseException e) {
            System.out.println("Received error from a server! " + e.getErrorResponse().getMsg());
        } catch (IOException e) {
            System.out.println("Something went wrong during I/O operations " + e);
        }
        return response;
    }
}
