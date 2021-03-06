package usr.pashik.securd.redis.command.meta.fabric;

import usr.pashik.securd.redis.command.RedisCommand;
import usr.pashik.securd.redis.command.info.RedisCommandFamily;
import usr.pashik.securd.redis.command.info.RedisCommandMnemonic;
import usr.pashik.securd.redis.command.info.RedisCommandType;
import usr.pashik.securd.redis.command.meta.RedisCommandFabric;
import usr.pashik.securd.redis.command.meta.command.UnknownRedisCommand;
import usr.pashik.securd.redis.protocol.object.RedisObject;

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

    @Override
    public String toString() {
        return String.format("%-30s [mnemonic=%20s, type=%15s, family=%15s]",
                             "UnknownRedisCommandFabric",
                             mnemonic,
                             type,
                             family);
    }
}
