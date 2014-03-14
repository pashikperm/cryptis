package usr.pashik.securd.proxy.clientprocessor;

import usr.pashik.securd.platform.configurator.ConfiguratorService;
import usr.pashik.securd.platform.thread.InjectedRunnable;
import usr.pashik.securd.redis.connection.RedisChannel;
import usr.pashik.securd.redis.connection.RedisChannelService;
import usr.pashik.securd.redis.exception.RedisAuthException;
import usr.pashik.securd.redis.exception.RedisProtocolReadException;
import usr.pashik.securd.redis.exception.RedisProtocolWriteException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by pashik on 10.03.14 16:50.
 */
@ApplicationScoped
public class ClientProcessorFabric {
    @Inject
    ConfiguratorService config;
    @Inject
    RedisChannelService redisChannelService;

    private SecureClientProcessor buildSecure(RedisChannel client, RedisChannel server) {
        SecureClientProcessor clientProcessor = new SecureClientProcessor();
        clientProcessor.client = client;
        clientProcessor.server = server;
        return clientProcessor;
    }

    private TransparentClientProcessor buildTransparent(RedisChannel client, RedisChannel server) {
        TransparentClientProcessor clientProcessor = new TransparentClientProcessor();
        clientProcessor.client = client;
        clientProcessor.server = server;
        return clientProcessor;
    }

    public InjectedRunnable build(Socket clientSocket) throws IOException, RedisProtocolReadException, RedisAuthException, RedisProtocolWriteException {
        return build(clientSocket, redisChannelService.getAuthedServerChannel());
    }

    public InjectedRunnable build(Socket clientSocket, RedisChannel server) throws IOException {
        RedisChannel client = new RedisChannel(clientSocket);
        return config.isSecureMode() ? buildSecure(client, server) : buildTransparent(client, server);
    }

}
