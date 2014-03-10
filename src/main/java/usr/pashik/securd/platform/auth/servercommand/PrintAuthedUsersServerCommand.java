package usr.pashik.securd.platform.auth.servercommand;

import usr.pashik.securd.platform.commandengine.ServerCommand;

/**
 * Created by pashik on 10.03.14 17:45.
 */
public class PrintAuthedUsersServerCommand extends ServerCommand {
    @Override
    public String getName() {
        return "authed";
    }

    @Override
    public String execute(String[] args) {
        return null;
    }
}
