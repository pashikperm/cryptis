package usr.pashik.securd.platform.commandengine;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by pashik on 10.03.14 1:45.
 */
@ApplicationScoped
public class ServerCommandEngine {
    final Map<String, ServerCommand> availableCommands;

    public ServerCommandEngine() {
        availableCommands = new HashMap<>();
    }

    public String parseAndExecuteCommand(String rawCommand) {
        String commandName = getCommandName(rawCommand);
        String[] args = getCommandArguments(rawCommand);

        ServerCommand command = findCommand(commandName);
        if (command == null) {
            return String.format("Unknown servercommand = %s", command);
        }

        return command.execute(args);
    }

    public void registerCommand(ServerCommand command) {
        availableCommands.put(command.getName(), command);
    }

    private String getCommandName(String rawCommand) {
        StringTokenizer tokenizer = new StringTokenizer(rawCommand);
        return tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
    }

    private String[] getCommandArguments(String rawCommand) {
        StringTokenizer tokenizer = new StringTokenizer(rawCommand);
        if (tokenizer.countTokens() < 2) {
            return new String[0];
        }
        String[] result = new String[tokenizer.countTokens() - 1];
        tokenizer.nextToken();
        for (int i = 0; i < tokenizer.countTokens() - 1; i++) {
            result[i] = tokenizer.nextToken();
        }
        return result;
    }

    private ServerCommand findCommand(String name) {
        for (Map.Entry<String, ServerCommand> entry : availableCommands.entrySet()) {
            if (entry.getKey().startsWith(name)) {
                return entry.getValue();
            }
        }
        return null;
    }

}
