package requestLogic.requestSenders;

import commandLogic.CommandDescription;
import exceptions.GotAnErrorResponseException;
import exceptions.ProceedException;
import requests.ArgumentCommandClientRequest;
import responses.CommandStatusResponse;
import serverLogic.ServerConnection;

import java.io.IOException;
import java.io.Serializable;
import java.net.PortUnreachableException;

public class ArgumentRequestSender<T extends Serializable> {

    public CommandStatusResponse sendCommand(CommandDescription command, String[] args, T argument, ServerConnection connection) {
        CommandStatusResponse response = null;
        try {
            ArgumentCommandClientRequest<T> rq = new ArgumentCommandClientRequest<>(command, args, argument);
            System.out.println("Sending command request...");
            response = (CommandStatusResponse) new RequestSender().sendRequest(rq, connection);
        } catch (PortUnreachableException e) {
            System.out.println("Server is unavailable. Please, wait until server will came back.");
        } catch (GotAnErrorResponseException e) {
            System.out.println("Received error from a server! " + e.getErrorResponse().getMsg());
        } catch (ProceedException e) {
            System.out.println("We've lost some packets during getting response: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Something went wrong during I/O operations.");
        }
        return response;
    }
}
