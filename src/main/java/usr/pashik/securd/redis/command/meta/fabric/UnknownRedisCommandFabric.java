package usr.pashik.securd.redis.command.meta.fabric;

import usr.pashik.securd.redis.command.RedisCommand;
import usr.pashik.securd.redis.command.info.RedisCommandFamily;
import usr.pashik.securd.redis.command.info.RedisCommandMnemonic;
import usr.pashik.securd.redis.command.info.RedisCommandType;
import usr.pashik.securd.redis.command.meta.RedisCommandFabric;
import usr.pashik.securd.redis.command.meta.command.UnknownRedisCommand;
import usr.pashik.securd.redis.protocol.response.RedisObject;

/**
 * Created by pashik on 12.03.14 0:01.
 */
public class UnknownRedisCommandFabric extends RedisCommandFabric {

    public UnknownRedisCommandFabric() {
        this.mnemonic = RedisCommandMnemonic.UNKNOWN;
        this.type = RedisCommandType.UNKNOWN;
        this.family = RedisCommandFamily.UNKNOWN;
    }

    @Override
    public RedisCommand build(RedisObject raw) {
        return new UnknownRedisCommand(raw);
    }

    @Override
    public RedisCommand create(Object... args) {
        return null;
    }
}
