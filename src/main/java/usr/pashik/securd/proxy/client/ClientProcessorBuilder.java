package usr.pashik.securd.proxy.client;

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

//    public static ClientProcessor build(RedisChannel client, RedisChannel server) {
//        ClientProcessor clientProcessor = new ClientProcessor();
//        clientProcessor.client = client;
//        clientProcessor.server = server;
//        return clientProcessor;
//    }
//
//    public static ClientProcessor build(Socket clientSocket) throws IOException {
//        RedisChannel client = new RedisChannel(clientSocket);
//        RedisChannel server = new RedisChannel();
//        return build(client, server);
//    }

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
