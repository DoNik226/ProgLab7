package commandManager;

import commandLogic.CommandDescription;
import commandManager.commandPreProcessing.CommandPreProcessorManager;
import commandManager.commands.*;
import exceptions.UnknownCommandException;
import requestLogic.CallerBack;
import requests.CommandClientRequest;
import responseLogic.responseSenders.ResponseSender;
import responses.CommandStatusResponse;
import serverLogic.ServerConnections;

import java.util.LinkedHashMap;
import java.util.Optional;

/**
 * Command Manager for interactive collection manage.
 *
 * @author Nikita
 * @since 1.0
 */
public class CommandManager {

    final LinkedHashMap<String, Cmd> commands;

    /**
     * Setup command manager and all of its commands.
     */
    public CommandManager() {
        commands = new LinkedHashMap<>();

        commands.put("help", new HelpCmd());
        commands.put("info", new InfoCmd());
        commands.put("show", new ShowCmd());
        commands.put("add", new AddCmd());
        commands.put("update", new UpdateCmd());
        commands.put("remove_by_id", new RemoveByIdCmd());
        commands.put("clear", new ClearCmd());
        commands.put("execute_script", new ExecuteScriptCmd());
        commands.put("exit", new ExitCmd());
        commands.put("add_if_max", new AddIfMaxCmd());
        commands.put("remove_lower", new RemoveLowerCmd());
        commands.put("remove_greater", new RemoveGreaterCmd());
        commands.put("sum_of_tuned_in_works", new SumOfTunedInWorksCmd());
        commands.put("count_by_minimal_point", new CountByMinimalPointCmd());
        commands.put("print_field_descending_minimal_point", new PrintFieldDescendingMinimalPointCmd());
    }

    /**
     * Get all commands from manager.
     *
     * @return Map of loaded commands
     */
    public LinkedHashMap<String, Cmd> getCommands() {
        return commands;
    }

    /**
     * Universe method for command executing.
     *
     * @param command request
     */
    public void executeCommand(CommandClientRequest command, CallerBack requester, ServerConnections answerConnection) {
        CommandStatusResponse response;
        try {
            CommandPreProcessorManager manager = new CommandPreProcessorManager();
            Cmd baseCommand = Optional.ofNullable(commands.get(command.getCommandDescription().getName())).orElseThrow(()
                    -> new UnknownCommandException("Указанная команда не была обнаружена"));
            manager.preProceed(baseCommand, requester, answerConnection);
            baseCommand.execute(command.getLineArgs());
            response = baseCommand.getResponse();
        } catch (IllegalArgumentException | NullPointerException e) {
            response = new CommandStatusResponse("Выполнение команды пропущено из-за неправильных предоставленных аргументов! (" + e.getMessage() + ")", -90);
            System.out.println(response.getResponse() + e);
        } catch (IndexOutOfBoundsException e) {
            response = new CommandStatusResponse("В команде предоставлено неправильное количество аргументов. Возможно, вам нужно обновить клиент", -91);
            System.out.println(response.getResponse() + e);
        } catch (Exception e) {
            response = new CommandStatusResponse("В командном менеджере произошла непредвиденная ошибка!", -92);
            System.out.println(response.getResponse() + e);
        }
        ResponseSender.sendResponse(response, answerConnection, requester);
    }

    public Cmd fromDescription(CommandDescription description) {
        return commands.get(description.getName());
    }
}