package commandManager.commands;

import clientLogic.SessionHandler;
import responses.CommandStatusResponse;

/**
 * Terminates the application (without saving collection).
 *
 * @author Nikita
 * @since 1.0
 */
public class ExitCmd implements Cmd, AuthorizableCommand {
    private CommandStatusResponse response;
    private long callerBackID;

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescr() {
        return "Invoke a logout and terminate the application";
    }

    @Override
    public void execute(String[] args) {
        System.out.println("Invoked exit command.");
        SessionHandler.getInstance().removeSession(callerBackID);
        response = CommandStatusResponse.ofString("Prepared for exit!");
    }

    @Override
    public CommandStatusResponse getResponse() {
        return response;
    }

    @Override
    public void setCallerID(long id) {
        this.callerBackID = id;
    }
}
