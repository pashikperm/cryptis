package usr.pashik.securd.platform.commandengine.exception;

/**
 * Created by pashik on 14.03.14 22:51.
 */
public class UnknownServerCommand extends Exception {
    String commandName;

    public UnknownServerCommand(String commandName) {
        this.commandName = commandName;
    }

    @Override
    public String getMessage() {
        return "Unknown server command '" + commandName + "'";
    }
}
