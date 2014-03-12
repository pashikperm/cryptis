package usr.pashik.securd.redis.command.impl.connection;

import org.jboss.weld.environment.se.events.ContainerInitialized;
import usr.pashik.securd.redis.command.RedisCommandService;
import usr.pashik.securd.redis.command.info.RedisCommandFamily;
import usr.pashik.securd.redis.command.info.RedisCommandMnemonic;
import usr.pashik.securd.redis.command.info.RedisCommandType;
import usr.pashik.securd.redis.command.meta.fabric.NoKeyRedisCommandFabric;
import usr.pashik.securd.redis.command.meta.fabric.PrimaryKeyRedisCommandFabric;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * Created by pashik on 12.03.14 0:23.
 */
public class ConnectionCommandSet {
    @Inject
    RedisCommandService commandService;

    public void onInitialized(@Observes ContainerInitialized initialized) {
        commandService.registerFabric(new NoKeyRedisCommandFabric(RedisCommandMnemonic.PING,
                                                                  RedisCommandType.SERVICE,
                                                                  RedisCommandFamily.CONNECTION));

        commandService.registerFabric(new PrimaryKeyRedisCommandFabric(RedisCommandMnemonic.AUTH,
                                                                       RedisCommandType.SERVICE,
                                                                       RedisCommandFamily.CONNECTION));

        commandService.registerFabric(new PrimaryKeyRedisCommandFabric(RedisCommandMnemonic.ECHO,
                                                                       RedisCommandType.SERVICE,
                                                                       RedisCommandFamily.CONNECTION));
    }
}
