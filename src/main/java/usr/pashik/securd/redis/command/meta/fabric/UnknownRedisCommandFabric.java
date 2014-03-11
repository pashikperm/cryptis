package usr.pashik.securd.redis.command.meta.fabric;

import usr.pashik.securd.redis.command.RedisCommand;
import usr.pashik.securd.redis.command.meta.RedisCommandFabric;
import usr.pashik.securd.redis.command.meta.command.UnknownRedisCommand;
import usr.pashik.securd.redis.protocol.response.RedisObject;

/**
 * Created by pashik on 12.03.14 0:01.
 */
public class UnknownRedisCommandFabric extends RedisCommandFabric {
    @Override
    public RedisCommand build(RedisObject raw) {
        return new UnknownRedisCommand(raw);
    }
}
