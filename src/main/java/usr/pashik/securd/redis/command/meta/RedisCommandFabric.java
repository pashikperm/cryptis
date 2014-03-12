package usr.pashik.securd.redis.command.meta;

import usr.pashik.securd.redis.command.RedisCommand;
import usr.pashik.securd.redis.command.info.RedisCommandFamily;
import usr.pashik.securd.redis.command.info.RedisCommandMnemonic;
import usr.pashik.securd.redis.command.info.RedisCommandType;
import usr.pashik.securd.redis.protocol.response.RedisObject;

/**
 * Created by pashik on 12.03.14 0:01.
 */
public abstract class RedisCommandFabric {
    protected RedisCommandMnemonic mnemonic;
    protected RedisCommandType type;
    protected RedisCommandFamily family;

    public abstract RedisCommand build(RedisObject raw);

    public RedisCommandMnemonic getMnemonic() {
        return mnemonic;
    }

    @Override
    public String toString() {
        return String.format("RedisCommandFabric [mnemonic=%20s, type=%15s, family=%15s]", mnemonic, type, family);
    }
}
