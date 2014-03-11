package usr.pashik.securd.redis.command.meta;

import usr.pashik.securd.redis.command.RedisCommand;
import usr.pashik.securd.redis.command.RedisCommandService;
import usr.pashik.securd.redis.command.info.RedisCommandFamily;
import usr.pashik.securd.redis.command.info.RedisCommandMnemonic;
import usr.pashik.securd.redis.command.info.RedisCommandType;
import usr.pashik.securd.redis.protocol.response.RedisObject;

import javax.inject.Inject;

/**
 * Created by pashik on 12.03.14 0:01.
 */
public abstract class RedisCommandFabric {
    @Inject
    RedisCommandService commandService;

    protected RedisCommandMnemonic mnemonic;
    protected RedisCommandType type;
    protected RedisCommandFamily family;

    public abstract RedisCommand build(RedisObject raw);

    public RedisCommandMnemonic getMnemonic() {
        return mnemonic;
    }

    public void registerSelf() {
//        commandService.registerFabric(this);
    }

    @Override
    public String toString() {
        return String.format("RedisCommandFabric [mnemonic=%s, type=%s, familly=%s]", mnemonic, type, family);
    }
}
