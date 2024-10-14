package commandManager.commands;

import commandManager.CommandManager;
import responses.CommandStatusResponse;

import java.util.Optional;

/**
 * Shows reference about available commands.
 *
 * @author Nikita
 * @since 1.0
 */
public class HelpCmd implements Cmd {
    private CommandStatusResponse response;

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescr() {
        return "Shows reference about available commands.";
    }

    @Override
    public void execute(String[] args) {
        CommandManager manager = new CommandManager();

        StringBuilder sb = new StringBuilder();

        if (args.length == 1) {
            manager.getCommands().forEach((name, command) -> sb.append(name).append(" ").append(command.getArgs()).append(" --  ").append(command.getDescr()).append('\n'));
        } else {
            for (int i = 1; i < args.length; i++) {
                var command = manager.getCommands().get(args[i]);
                sb.append(args[i]).append(" -- ").append(Optional.ofNullable(command).map(Cmd::getDescr).orElse("This command is not found in manager")).append('\n');
            }
        }

        response = CommandStatusResponse.ofString(sb.toString());
        System.out.println(response.getResponse());
    }

    @Override
    public CommandStatusResponse getResponse() {
        return response;
    }
}
