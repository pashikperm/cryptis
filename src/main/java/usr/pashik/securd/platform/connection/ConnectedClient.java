package usr.pashik.securd.platform.connection;

import java.net.Socket;

/**
 * Created by pashik on 10.03.14 0:45.
 */
public class ConnectedClient {
    Socket socket;

    ConnectedClient(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }
}
