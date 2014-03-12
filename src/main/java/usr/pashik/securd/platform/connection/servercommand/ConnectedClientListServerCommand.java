package usr.pashik.securd.platform.connection.servercommand;

import usr.pashik.securd.platform.commandengine.ServerCommand;
import usr.pashik.securd.platform.connection.ConnectedClient;
import usr.pashik.securd.platform.connection.ConnectedClientService;

import javax.inject.Inject;
import java.util.Set;

/**
 * Created by pashik on 12.03.14 12:18.
 */
public class ConnectedClientListServerCommand extends ServerCommand {
    @Inject
    ConnectedClientService clientService;

    @Override
    public String getName() {
        return "clients";
    }

    @Override
    public String getDescription() {
        return "Prints all opened clients' connections";
    }

    @Override
    public String execute(String[] args) {
        Set<ConnectedClient> clientSet = clientService.getClients();
        StringBuilder result = new StringBuilder("Client list:\n");
        for (ConnectedClient client : clientSet) {
            result.append(client);
            result.append("\n");
        }
        return result.toString();
    }
}
