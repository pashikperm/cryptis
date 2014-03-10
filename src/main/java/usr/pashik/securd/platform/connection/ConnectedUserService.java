package usr.pashik.securd.platform.connection;

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
public class ConnectedUserService {
    @Inject
    AuthUserService authUserService;

    Set<ConnectedClient> connectedClients = new HashSet<>();

    public ConnectedClient registerConnection(Socket client) {
        ConnectedClient connectedClient = new ConnectedClient(client);
        connectedClients.add(connectedClient);
        return connectedClient;
    }

    public void unregisterConnection(ConnectedClient user) {
        connectedClients.remove(user);
        // TODO: FIRE EVENT UNREGISTERED USER
        authUserService.unregisterUser(user);
    }
}
