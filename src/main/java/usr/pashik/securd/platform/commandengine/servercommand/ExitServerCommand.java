package usr.pashik.securd.platform.commandengine.servercommand;

import usr.pashik.securd.platform.commandengine.ServerCommand;

/**
 * Created by pashik on 10.03.14 14:44.
 */
public class ExitServerCommand extends ServerCommand {

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String execute(String[] args) {
        System.exit(0);
        return "Closed";
    }
}
