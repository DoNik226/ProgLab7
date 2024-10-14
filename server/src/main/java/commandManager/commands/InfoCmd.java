package commandManager.commands;

import models.LabWork;
import models.handlers.CollectionHandler;
import models.handlers.LabWorksHandler;
import responses.CommandStatusResponse;

import java.util.HashSet;

/**
 * Shows information about the collection.
 *
 * @author Nikita
 * @since 1.0
 */
public class InfoCmd implements Cmd {
    private CommandStatusResponse response;

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescr() {
        return "Shows information about the collection.";
    }

    @Override
    public void execute(String[] args) {
        CollectionHandler<HashSet<LabWork>, LabWork> handler = LabWorksHandler.getInstance();

        HashSet<LabWork> collection = handler.getCollection();

        String sb = "Now you are operating with collection of type " + collection.getClass().getName() + ", filled with elements of type " + handler.getFirstOrNew().getClass().getName() + '\n' +
                "Size of the collection is " + collection.size() + '\n' +
                "Init date: " + handler.getInitDate();

        response = CommandStatusResponse.ofString(sb);
        System.out.println(response.getResponse());
    }

    @Override
    public CommandStatusResponse getResponse() {
        return response;
    }
}

