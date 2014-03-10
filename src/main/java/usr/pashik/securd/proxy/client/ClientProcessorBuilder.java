package usr.pashik.securd.proxy.client;

import usr.pashik.securd.platform.configurator.ConfiguratorService;
import usr.pashik.securd.redis.connection.RedisServer;
import usr.pashik.securd.redis.connection.RedisUser;

import javax.inject.Inject;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by pashik on 10.03.14 16:50.
 */
public class ClientProcessorBuilder {
    @Inject
    ConfiguratorService config;

    public static ClientProcessor build(RedisUser client, RedisServer server) {
        ClientProcessor clientProcessor = new ClientProcessor();
        clientProcessor.client = client;
        clientProcessor.server = server;
        return clientProcessor;
    }

    public static ClientProcessor build(Socket clientSocket) throws IOException {
        RedisUser client = new RedisUser(clientSocket);
        RedisServer server = new RedisServer();
        return build(client, server);
    }
}
