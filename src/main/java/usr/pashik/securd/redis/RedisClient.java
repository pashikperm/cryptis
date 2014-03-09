package usr.pashik.securd.redis;

import usr.pashik.securd.redis.protocol.RESPClient;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by pashik on 10.03.14 0:33.
 */
public class RedisClient extends RESPClient {
    public RedisClient() throws IOException {
        super();
    }

    public RedisClient(Socket socket) throws IOException {
        super(socket);
    }
}
