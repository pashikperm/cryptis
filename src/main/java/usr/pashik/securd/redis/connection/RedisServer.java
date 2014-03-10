package usr.pashik.securd.redis.connection;

import usr.pashik.securd.redis.protocol.RESPClient;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by pashik on 10.03.14 0:33.
 */
public class RedisServer extends RESPClient {
    public RedisServer() throws IOException {
        super();
    }

    public RedisServer(Socket socket) throws IOException {
        super(socket);
    }
}
