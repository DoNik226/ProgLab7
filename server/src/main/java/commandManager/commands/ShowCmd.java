package commandManager.commands;

import models.LabWork;
import models.handlers.CollectionHandler;
import models.handlers.LabWorksHandler;
import responses.CommandStatusResponse;
import mains.Utilities;

import java.util.HashSet;

/**
 * Shows every element of the collection in toString() interpretation.
 *
 * @author Nikita
 * @since 1.0
 */
public class ShowCmd implements Cmd {
    private CommandStatusResponse response;

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getArgs() {
        return "[page]";
    }

    @Override
    public String getDescr() {
        return "Shows elements (up to 50 elements) of the collection in toString() interpretation.";
    }

    @Override
    public void execute(String[] args) {
        CollectionHandler<HashSet<LabWork>, LabWork> handler = LabWorksHandler.getInstance();

        StringBuilder sb = new StringBuilder();

        Long pageNumber = 0L;

        if (handler.getCollection().size() > 50 && args.length == 1) {
            sb.append("The collection is too large. Sending only 50 elements. " +
                    "Use show [page] for variate displaying.");
        }

        if (args.length > 1) {
            pageNumber = Utilities.handleUserInputID(args[1]);

            if (pageNumber == null) {
                response = new CommandStatusResponse(
                        "You must enter a valid page number", -6);
                return;
            }
        }

        handler.getSorted().stream().skip(pageNumber * 50).limit(50)
                .forEach(e -> sb.append(e.toString()).append('\n'));
        response = CommandStatusResponse.ofString(sb.toString());

        if (sb.isEmpty()) {
            response = CommandStatusResponse.ofString("There's nothing to show.");
        }

        System.out.println(response.getResponse());
    }

    @Override
    public CommandStatusResponse getResponse() {
        return response;
    }
}
