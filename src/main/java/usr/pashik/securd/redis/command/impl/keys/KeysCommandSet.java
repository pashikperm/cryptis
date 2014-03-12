package usr.pashik.securd.redis.command.impl.keys;

import org.jboss.weld.environment.se.events.ContainerInitialized;
import usr.pashik.securd.redis.command.RedisCommandService;
import usr.pashik.securd.redis.command.info.RedisCommandFamily;
import usr.pashik.securd.redis.command.info.RedisCommandMnemonic;
import usr.pashik.securd.redis.command.info.RedisCommandType;
import usr.pashik.securd.redis.command.meta.fabric.PrimaryKeyRedisCommandFabric;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * Created by pashik on 12.03.14 0:23.
 */
public class KeysCommandSet {
    @Inject
    RedisCommandService commandService;

    public void onInitialized(@Observes ContainerInitialized initialized) {
        commandService.registerFabric(new PrimaryKeyRedisCommandFabric(RedisCommandMnemonic.GET,
                                                                       RedisCommandType.READ,
                                                                       RedisCommandFamily.KEYS));
        commandService.registerFabric(new PrimaryKeyRedisCommandFabric(RedisCommandMnemonic.SET,
                                                                       RedisCommandType.WRITE,
                                                                       RedisCommandFamily.KEYS));
        commandService.registerFabric(new PrimaryKeyRedisCommandFabric(RedisCommandMnemonic.STRLEN,
                                                                       RedisCommandType.INFO,
                                                                       RedisCommandFamily.KEYS));
    }
}
