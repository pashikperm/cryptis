package usr.pashik.securd.redis.connection;

import usr.pashik.securd.platform.connection.ConnectedClient;
import usr.pashik.securd.redis.protocol.RESPClient;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by pashik on 10.03.14 0:33.
 */
public class RedisUser extends RESPClient {
    public RedisUser() throws IOException {
        super();
    }

    public RedisUser(Socket socket) throws IOException {
        super(socket);
    }

    public RedisUser(ConnectedClient user) throws IOException {
        super(user.socket);
    }
}
