package usr.pashik.securd.platform.commandengine.servercommand;

import usr.pashik.securd.platform.commandengine.ServerCommand;
import usr.pashik.securd.platform.commandengine.ServerCommandService;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Created by pashik on 12.03.14 12:26.
 */
public class HelpServerCommand extends ServerCommand {
    @Inject
    ServerCommandService commandService;

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Prints help about command engine";
    }

    @Override
    public String execute(String[] args) {
        Collection<ServerCommand> commandSet = commandService.getRegisteredCommands();
        StringBuilder result = new StringBuilder("Server command engine. " +
                                                         "Supports command by prefix, i.e. 'h'.\nCommand list:\n");
        for (ServerCommand command : commandSet) {
            result.append(String.format("ServerCommand [name=%15s, description=%s]\n",
                                        command.getName(),
                                        command.getDescription()));
        }
        return result.toString();
    }
}
