package commandManager.commands;

import fileLogic.Saver;
import models.LabWork;
import models.handlers.CollectionHandler;
import models.handlers.LabWorksHandler;
import responses.CommandStatusResponse;

import java.util.HashSet;

/**
 * Saves collection to file.
 *
 * @author Nikita
 * @since 1.0
 */
public class SaveCmd implements Cmd {
    private CommandStatusResponse response;

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescr() {
        return "Deprecated (for server-use only).";
    }

    @Override
    public void execute(String[] args) {
        System.out.println("Saving...");
        CollectionHandler<HashSet<LabWork>, LabWork> collectionHandler = LabWorksHandler.getInstance();
        Saver<HashSet<LabWork>, LabWork> saver = new Saver<>(LabWork.class);

        saver.saveCollection(collectionHandler.getCollection(), "laba6");

        response = CommandStatusResponse.ofString("[Server] Collection saving executed.");
        System.out.println(response.getResponse());
    }

    @Override
    public CommandStatusResponse getResponse() {
        return response;
    }
}
