package usr.pashik.securd.redis.command.meta.command;

import usr.pashik.securd.redis.command.RedisCommand;
import usr.pashik.securd.redis.command.info.RedisCommandFamily;
import usr.pashik.securd.redis.command.info.RedisCommandMnemonic;
import usr.pashik.securd.redis.command.info.RedisCommandType;
import usr.pashik.securd.redis.protocol.response.RedisObject;

/**
 * Created by pashik on 10.03.14 22:26.
 */
public class UnknownRedisCommand extends RedisCommand {

    public UnknownRedisCommand(RedisObject raw) {
        this.mnemonic = RedisCommandMnemonic.UNKNOWN;
        this.type = RedisCommandType.UNKNOWN;
        this.family = RedisCommandFamily.UNKNOWN;
        this.raw = raw;
    }

    @Override
    public String getPrimaryKey() {
        return null;
    }

    @Override
    public String getSecondaryKey() {
        return null;
    }

    @Override
    public RedisObject getArguments() {
        return null;
    }

    @Override
    public String toString() {
        return String.format("UnknownRedisCommand [mnemonic=%15s, type=%10s, familly=%15s]", mnemonic, type, family);
    }
}
