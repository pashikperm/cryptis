package usr.pashik.securd.platform.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import usr.pashik.securd.platform.auth.AuthUserService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by pashik on 10.03.14 0:34.
 */
@ApplicationScoped
public class ConnectedClientService {
    @Inject
    AuthUserService authUserService;

    Logger log = LogManager.getLogger(ConnectedClientService.class);

    Set<ConnectedClient> connectedClients = new HashSet<>();

    public ConnectedClient registerConnection(Socket client) {
        ConnectedClient connectedClient = new ConnectedClient(client);
        connectedClients.add(connectedClient);
        log.info(String.format("registerConnection %s", connectedClient));
        return connectedClient;
    }

    public void unregisterConnection(ConnectedClient connectedClient) {
        log.info(String.format("unregisterConnection %s", connectedClient));
        connectedClients.remove(connectedClient);
        // TODO: FIRE EVENT UNREGISTERED USER
        authUserService.unregisterUser(connectedClient);
    }

    public Set<ConnectedClient> getClients() {
        return connectedClients;
    }
}
