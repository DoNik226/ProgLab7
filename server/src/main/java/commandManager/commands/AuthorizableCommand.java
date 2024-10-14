package commandManager.commands;

import commandManager.commandPreProcessing.PreProcessingCommandInterface;

public interface AuthorizableCommand extends PreProcessingCommandInterface {
    void setCallerID(long id);
}
