package usr.pashik.securd.redis.command.meta.command;

import usr.pashik.securd.redis.command.RedisCommand;
import usr.pashik.securd.redis.command.info.RedisCommandFamily;
import usr.pashik.securd.redis.command.info.RedisCommandMnemonic;
import usr.pashik.securd.redis.command.info.RedisCommandType;
import usr.pashik.securd.redis.protocol.object.RedisObject;

/**
 * Created by pashik on 11.03.14 1:34.
 */
public class NoKeyRedisCommand extends RedisCommand {
    public NoKeyRedisCommand(RedisCommandMnemonic mnemonic, RedisCommandType type, RedisCommandFamily family, RedisObject raw) {
        this.mnemonic = mnemonic;
        this.type = type;
        this.family = family;
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
        return String.format("%15s [mnemonic=%15s, type=%10s, family=%15s]", "NoKeyRedisCommand", mnemonic, type, family);
    }
}
