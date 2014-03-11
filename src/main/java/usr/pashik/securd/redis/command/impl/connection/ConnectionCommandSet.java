package usr.pashik.securd.redis.command.impl.connection;

import org.jboss.weld.environment.se.events.ContainerInitialized;
import usr.pashik.securd.redis.command.info.RedisCommandFamily;
import usr.pashik.securd.redis.command.info.RedisCommandMnemonic;
import usr.pashik.securd.redis.command.info.RedisCommandType;
import usr.pashik.securd.redis.command.meta.fabric.NoKeyRedisCommandFabric;
import usr.pashik.securd.redis.command.meta.fabric.PrimaryKeyRedisCommandFabric;

import javax.enterprise.event.Observes;

/**
 * Created by pashik on 12.03.14 0:23.
 */
public class ConnectionCommandSet {
    public void onInitialized(@Observes ContainerInitialized initialized) {
        new NoKeyRedisCommandFabric(RedisCommandMnemonic.PING,
                                    RedisCommandType.SERVICE,
                                    RedisCommandFamily.CONNECTION).registerSelf();
        new PrimaryKeyRedisCommandFabric(RedisCommandMnemonic.AUTH,
                                         RedisCommandType.SERVICE,
                                         RedisCommandFamily.CONNECTION).registerSelf();
        new PrimaryKeyRedisCommandFabric(RedisCommandMnemonic.ECHO,
                                         RedisCommandType.SERVICE,
                                         RedisCommandFamily.CONNECTION).registerSelf();
    }
}
