package commandManager;

import commandLogic.CommandDescription;
import commandLogic.commandReceiverLogic.callers.ExternalArgumentReceiverCaller;
import commandLogic.commandReceiverLogic.callers.ExternalBaseReceiverCaller;
import models.LabWork;

import java.util.ArrayList;

public class CommandExporter {
    public static ArrayList<CommandDescription> getCommandsToExport() {
        ArrayList<CommandDescription> commands = new ArrayList<>();
        commands.add(new CommandDescription("help", new ExternalBaseReceiverCaller()));
        commands.add(new CommandDescription("info", new ExternalBaseReceiverCaller()));
        commands.add(new CommandDescription("show", new ExternalBaseReceiverCaller()));
        commands.add(new CommandDescription("remove_by_id", new ExternalBaseReceiverCaller()));
        commands.add(new CommandDescription("clear", new ExternalBaseReceiverCaller()));
        commands.add(new CommandDescription("execute_script", new ExternalBaseReceiverCaller()));
        commands.add(new CommandDescription("exit", new ExternalBaseReceiverCaller()));
        commands.add(new CommandDescription("sum_of_tuned_in_works", new ExternalBaseReceiverCaller()));
        commands.add(new CommandDescription("count_by_minimal_point", new ExternalBaseReceiverCaller()));
        commands.add(new CommandDescription("print_field_descending_minimal_point", new ExternalBaseReceiverCaller()));
        commands.add(new CommandDescription("add", new ExternalArgumentReceiverCaller<LabWork>()));
        commands.add(new CommandDescription("remove_lower", new ExternalArgumentReceiverCaller<LabWork>()));
        commands.add(new CommandDescription("add_if_max", new ExternalArgumentReceiverCaller<LabWork>()));
        commands.add(new CommandDescription("update", new ExternalArgumentReceiverCaller<LabWork>()));
        commands.add(new CommandDescription("remove_greater", new ExternalArgumentReceiverCaller<LabWork>()));

        return commands;
    }
}
