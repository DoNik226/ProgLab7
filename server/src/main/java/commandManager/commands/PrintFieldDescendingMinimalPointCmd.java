package commandManager.commands;

import models.LabWork;
import models.handlers.CollectionHandler;
import models.handlers.LabWorksHandler;
import responses.CommandStatusResponse;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

/**
 * Prints all minimalPoint fields in descending sorting.
 *
 * @author Nikita
 * @since 1.0
 */
public class PrintFieldDescendingMinimalPointCmd implements Cmd {
    private CommandStatusResponse response;

    @Override
    public String getName() {
        return "print_field_descending_minimal_point";
    }

    @Override
    public String getDescr() {
        return "Prints all minimalPoint fields in descending sorting.";
    }

    @Override
    public void execute(String[] args) {
        CollectionHandler<HashSet<LabWork>, LabWork> collectionHandler = LabWorksHandler.getInstance();
        List<Integer> distances = collectionHandler.getCollection().stream().map(LabWork::getMinimalPoint).sorted(Comparator.comparingInt(o -> (int)o).reversed()).toList();

        StringBuilder sb = new StringBuilder();
        distances.forEach(d -> sb.append(d).append('\n'));
        response = CommandStatusResponse.ofString(sb.toString());

        if (collectionHandler.getCollection().isEmpty())
            response = CommandStatusResponse.ofString("There's nothing to show...");

        System.out.println(response.getResponse());
    }

    @Override
    public CommandStatusResponse getResponse() {
        return response;
    }
}
