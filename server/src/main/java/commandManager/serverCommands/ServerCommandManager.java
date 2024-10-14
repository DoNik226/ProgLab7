package commandManager.serverCommands;

import commandManager.commands.Cmd;
import commandManager.serverCommands.commands.ShowSessions;
import exceptions.UnknownCommandException;

import java.util.LinkedHashMap;
import java.util.Optional;

public class ServerCommandManager {
    private final LinkedHashMap<String, Cmd> serverCommands;

    public ServerCommandManager() {
        serverCommands = new LinkedHashMap<>();
        serverCommands.put("sessions", new ShowSessions());
    }

    public void executeCommand(String[] args) throws UnknownCommandException {
        Optional.ofNullable(serverCommands.get(args[0])).orElseThrow(()
                -> new UnknownCommandException("Указанная команда не была обнаружена")).execute(args);
    }
}
