package usr.pashik.securd.platform.commands;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by pashik on 10.03.14 1:45.
 */
@ApplicationScoped
public class ServerCommandEngine {
    Map<String, ServerCommand> availableCommands;

    public ServerCommandEngine() {
        availableCommands = new HashMap<>();
        collectAvailableCommands();
    }

    public String parseAndExecuteCommand(String rawCommand) {
        String command = getCommandName(rawCommand);
        String[] args = getCommandArguments(rawCommand);

        if (!availableCommands.containsKey(command)) {
            return String.format("Unknown command = %s", command);
        }

        return availableCommands.get(command).execute(args);
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

    private void collectAvailableCommands() {

    }

}
