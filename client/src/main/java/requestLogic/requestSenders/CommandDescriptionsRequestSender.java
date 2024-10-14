package requestLogic.requestSenders;

import commandLogic.CommandDescription;
import exceptions.GotAnErrorResponseException;
import exceptions.ProceedException;
import requests.CommandDescriptionsRequest;
import responses.CommandDescriptionsResponse;
import serverLogic.ServerConnectionHandler;

import java.io.IOException;
import java.net.PortUnreachableException;
import java.util.ArrayList;

public class CommandDescriptionsRequestSender {

    public ArrayList<CommandDescription> sendRequestAndGetCommands() {
        ArrayList<CommandDescription> result = null;

        CommandDescriptionsRequest request = new CommandDescriptionsRequest();

        try {
            CommandDescriptionsResponse response = (CommandDescriptionsResponse) new RequestSender().sendRequest(request, ServerConnectionHandler.getCurrentConnection());
            result = response.getCommands();
        } catch (PortUnreachableException e) {
            System.out.println("Server is unavailable. Please, wait until server will came back.");
            System.out.println("We can't get available commands because the server is unavailable.");
        } catch (GotAnErrorResponseException e) {
            System.out.println("Received error from a server! " + e.getErrorResponse().getMsg());
        } catch (ProceedException e) {
            System.out.println("We've lost some packets during getting response: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Something went wrong during I/O operations.");
        }

        return result;
    }
}
