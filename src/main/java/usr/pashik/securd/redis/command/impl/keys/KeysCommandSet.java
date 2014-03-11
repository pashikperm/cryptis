package usr.pashik.securd.redis.command.impl.keys;

import org.jboss.weld.environment.se.events.ContainerInitialized;
import usr.pashik.securd.redis.command.info.RedisCommandFamily;
import usr.pashik.securd.redis.command.info.RedisCommandMnemonic;
import usr.pashik.securd.redis.command.info.RedisCommandType;
import usr.pashik.securd.redis.command.meta.fabric.PrimaryKeyRedisCommandFabric;

import javax.enterprise.event.Observes;

/**
 * Created by pashik on 12.03.14 0:23.
 */
public class KeysCommandSet {
    public void onInitialized(@Observes ContainerInitialized initialized) {
        new PrimaryKeyRedisCommandFabric(RedisCommandMnemonic.GET,
                                         RedisCommandType.READ,
                                         RedisCommandFamily.KEYS).registerSelf();
        new PrimaryKeyRedisCommandFabric(RedisCommandMnemonic.SET,
                                         RedisCommandType.WRITE,
                                         RedisCommandFamily.KEYS).registerSelf();
        new PrimaryKeyRedisCommandFabric(RedisCommandMnemonic.STRLEN,
                                         RedisCommandType.INFO,
                                         RedisCommandFamily.KEYS).registerSelf();
    }
}
