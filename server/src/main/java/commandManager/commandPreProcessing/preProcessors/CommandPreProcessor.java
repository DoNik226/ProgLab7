package commandManager.commandPreProcessing.preProcessors;

import commandManager.commands.Cmd;
import exceptions.PreProceedingFailedException;
import exceptions.ProcessionInterruptedException;
import requestLogic.CallerBack;
import serverLogic.ServerConnections;

public interface CommandPreProcessor {
    void proceed(Cmd command, CallerBack callerBack, ServerConnections connection) throws PreProceedingFailedException, ProcessionInterruptedException;
}
