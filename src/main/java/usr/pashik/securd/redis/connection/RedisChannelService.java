package usr.pashik.securd.redis.connection;

import usr.pashik.securd.platform.configurator.ConfiguratorService;
import usr.pashik.securd.redis.command.RedisCommand;
import usr.pashik.securd.redis.command.RedisCommandService;
import usr.pashik.securd.redis.command.info.RedisCommandMnemonic;
import usr.pashik.securd.redis.command.meta.RedisCommandFabric;
import usr.pashik.securd.redis.exception.RedisAuthException;
import usr.pashik.securd.redis.exception.RedisProtocolReadException;
import usr.pashik.securd.redis.exception.RedisProtocolWriteException;
import usr.pashik.securd.redis.protocol.response.RedisObject;
import usr.pashik.securd.redis.protocol.response.RedisObjectType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by pashik on 12.03.14 23:18.
 */
@ApplicationScoped
public class RedisChannelService {
    @Inject
    ConfiguratorService config;
    @Inject
    RedisCommandService commandService;

    public RedisChannel getAuthedServerChannel() throws IOException, RedisProtocolWriteException, RedisProtocolReadException, RedisAuthException {
        RedisChannel serverChannel = getServerChannel();

        RedisCommandFabric fabric = commandService.getCommandFabric(RedisCommandMnemonic.AUTH);
        RedisCommand authCommand = fabric.create(config.getServerPassword());
        serverChannel.sendCommand(authCommand);
        RedisObject response = serverChannel.readResponse();
        if (response.type == RedisObjectType.ERROR) {
            throw new RedisAuthException();
        }
        return serverChannel;
    }

    public RedisChannel getServerChannel() throws IOException {
        Socket serverSocket = new Socket(config.getServerHost(), config.getServerPort());
        RedisChannel serverChannel = new RedisChannel(serverSocket);
        return serverChannel;
    }
}
