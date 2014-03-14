package usr.pashik.securd.access.servercommand;

import usr.pashik.securd.platform.commandengine.ServerCommand;

/**
 * Created by pashik on 10.03.14 17:42.
 */
public class AccessRulesListServerCommand extends ServerCommand {
    @Override
    public String getName() {
        return "rules";
    }

    @Override
    public String getDescription() {
        return "Prints all access rules";
    }

    @Override
    public String execute(String[] args) {
        return null;
    }
}
