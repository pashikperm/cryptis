package usr.pashik.securd.proxy.clientprocessor;

import usr.pashik.securd.platform.configurator.ConfiguratorService;
import usr.pashik.securd.redis.connection.RedisChannel;

import javax.inject.Inject;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by pashik on 10.03.14 16:50.
 */
public class ClientProcessorBuilder {
    @Inject
    ConfiguratorService config;

    public static SecureClientProcessor buildSecure(RedisChannel client, RedisChannel server) {
        SecureClientProcessor clientProcessor = new SecureClientProcessor();
        clientProcessor.client = client;
        clientProcessor.server = server;
        return clientProcessor;
    }

    public static SecureClientProcessor buildSecure(Socket clientSocket) throws IOException {
        RedisChannel client = new RedisChannel(clientSocket);
        RedisChannel server = new RedisChannel();
        return buildSecure(client, server);
    }

    public static TransparentClientProcessor buildTransparent(RedisChannel client, RedisChannel server) {
        TransparentClientProcessor clientProcessor = new TransparentClientProcessor();
        clientProcessor.client = client;
        clientProcessor.server = server;
        return clientProcessor;

    }

    public static TransparentClientProcessor buildTransparent(Socket clientSocket) throws IOException {
        RedisChannel client = new RedisChannel(clientSocket);
        RedisChannel server = new RedisChannel();
        return buildTransparent(client, server);
    }
}
