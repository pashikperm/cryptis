package usr.pashik.securd.redis.command;

import usr.pashik.securd.redis.command.info.RedisCommandFamily;
import usr.pashik.securd.redis.command.info.RedisCommandMnemonic;
import usr.pashik.securd.redis.command.info.RedisCommandType;
import usr.pashik.securd.redis.command.meta.RedisCommandFabric;
import usr.pashik.securd.redis.command.meta.fabric.UnknownRedisCommandFabric;
import usr.pashik.securd.redis.protocol.object.RedisObject;
import usr.pashik.securd.redis.protocol.object.RedisObjectType;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pashik on 11.03.14 23:18.
 */
@ApplicationScoped
public class RedisCommandService {
    Map<String, RedisCommandFabric> commands;
    UnknownRedisCommandFabric unknownFabric;

    RedisCommandService() {
        commands = new HashMap<>();
        unknownFabric = new UnknownRedisCommandFabric();
        registerFabric(unknownFabric);
    }

    public RedisCommand getCommand(RedisObject redisObject) {
        String mnemonic = commandMnemonic(redisObject);
        RedisCommandFabric fabric = commands.get(mnemonic);
        if (fabric == null) {
            fabric = unknownFabric;
        }
        return fabric.build(redisObject);
    }

    public RedisCommandFabric getCommandFabric(RedisCommandMnemonic commandMnemonic) {
        return commands.get(commandMnemonic.name());
    }

    public RedisCommandType getCommandType(RedisCommandMnemonic commandMnemonic) {
        return getCommandFabric(commandMnemonic).type;
    }

    public RedisCommandFamily getCommandFamily(RedisCommandMnemonic commandMnemonic) {
        return getCommandFabric(commandMnemonic).family;
    }

    public void registerFabric(RedisCommandFabric fabric) {
        commands.put(fabric.getMnemonic().name(), fabric);
    }

    private String commandMnemonic(RedisObject redisObject) {
        if (redisObject.type != RedisObjectType.ARRAY ||
                redisObject.avalue.length < 1 ||
                redisObject.avalue[0].type != RedisObjectType.BULK) {
            return null;
        }
        return redisObject.avalue[0].bsvalue.toUpperCase();
    }

    public Collection<RedisCommandFabric> getRegistered() {
        return commands.values();
    }
}
