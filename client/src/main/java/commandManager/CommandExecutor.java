package commandManager;

import commandLogic.CommandDescription;
import commandLogic.commandReceiverLogic.ReceiverManager;
import commandLogic.commandReceiverLogic.enums.ReceiverType;
import commandLogic.commandReceiverLogic.handlers.ArgumentReceiverHandler;
import commandLogic.commandReceiverLogic.handlers.NonArgReceiversHandler;
import commandManager.externalReceivers.ArgumentLabWorkCommandReceiver;
import commandManager.externalReceivers.ExecuteScriptReceiver;
import commandManager.externalReceivers.ExitReceiver;
import commandManager.externalReceivers.NonArgumentReceiver;
import exceptions.*;
import models.LabWork;
import models.handlers.ModuleHandler;
import models.handlers.nonUserMode.LabWorkNonCLIHandler;
import models.handlers.userMode.LabWorkCLIHandler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import static commandManager.CommandMode.CLI_UserMode;

/**
 * Class for executing commands. Provides different inputs for command executing.
 */
public class CommandExecutor {

    private final ArrayList<CommandDescription> commands;
    private final Scanner scannerInput;
    private final CommandMode mode;
    private final ReceiverManager manager;

    /**
     * Constructor :/
     *
     * @param commands array of commands
     * @param input    commands stream (File, System.in, e.t.c.)
     * @param mode     variant of command behavior (see CommandMode enum)
     */
    public CommandExecutor(ArrayList<CommandDescription> commands, InputStream input, CommandMode mode) throws CommandsNotLoadedException {
        if (commands == null) throw new CommandsNotLoadedException();

        this.commands = commands;
        this.scannerInput = new Scanner(input);
        this.mode = mode;
        manager = new ReceiverManager();

        manager.registerHandler(ReceiverType.NoArgs, new NonArgReceiversHandler());
        manager.registerHandler(ReceiverType.ArgumentLabWork, new ArgumentReceiverHandler<>(LabWork.class));

        manager.registerReceiver(ReceiverType.NoArgs, new NonArgumentReceiver());
        manager.registerReceiver(ReceiverType.NoArgs, new ExecuteScriptReceiver());
        manager.registerReceiver(ReceiverType.NoArgs, new ExitReceiver());

        ModuleHandler<LabWork> handler = null;
        switch (mode) {
            case CLI_UserMode -> handler = new LabWorkCLIHandler();
            case NonUserMode -> handler = new LabWorkNonCLIHandler(scannerInput);
        }
        manager.registerReceiver(ReceiverType.ArgumentLabWork, new ArgumentLabWorkCommandReceiver(handler));
    }

    /**
     * Start executing commands from InputStream.
     */
    public void startExecuting() {
        while (scannerInput.hasNext()) {
            String line = scannerInput.nextLine();
            if (line.isEmpty()) continue;
            try {
                try {
                    String[] lineArgs = line.split(" ");
                    CommandDescription description = Optional.ofNullable(commands).orElseThrow(CommandsNotLoadedException::new).stream().filter(x -> x.getName().equals(lineArgs[0])).findAny().orElseThrow(() -> new UnknownCommandExceptions("Указанная команда не была обнаружена"));
                    description.getReceiver().callReceivers(manager, description, lineArgs);
                } catch (IllegalArgumentException | NullPointerException e) {
                    System.out.println("Выполнение команды пропущено из-за неправильных предоставленных аргументов! (" + e.getMessage() + ")");
                    throw new CommandInterruptedException(e);
                } catch (BuildObjectException | UnknownCommandExceptions e) {
                    System.out.println(e.getMessage());
                    throw new CommandInterruptedException(e);
                } catch (WrongAmountOfArgumentsException e) {
                    System.out.println("Wrong amount of arguments! " + e.getMessage());
                    throw new CommandInterruptedException(e);
                } catch (Exception e) {
                    System.out.println("В командном менеджере произошла непредвиденная ошибка! " + e.getMessage());
                    throw new CommandInterruptedException(e);
                }
            } catch (CommandInterruptedException ex) {
                if (mode.equals(CLI_UserMode))
                    System.out.println("Выполнение команды было прервано. Вы можете продолжать работу. Программа возвращена в безопасное состояние.");
                else
                    System.out.println("Команда была пропущена... Обработчик продолжает работу");
            }
        }
    }
}
