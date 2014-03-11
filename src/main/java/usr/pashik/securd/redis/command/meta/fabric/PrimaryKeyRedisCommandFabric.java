package usr.pashik.securd.redis.command.meta.fabric;

import usr.pashik.securd.redis.command.RedisCommand;
import usr.pashik.securd.redis.command.info.RedisCommandFamily;
import usr.pashik.securd.redis.command.info.RedisCommandMnemonic;
import usr.pashik.securd.redis.command.info.RedisCommandType;
import usr.pashik.securd.redis.command.meta.RedisCommandFabric;
import usr.pashik.securd.redis.command.meta.command.PrimaryKeyRedisCommand;
import usr.pashik.securd.redis.protocol.response.RedisObject;

/**
 * Created by pashik on 12.03.14 0:01.
 */
public class PrimaryKeyRedisCommandFabric extends RedisCommandFabric {

    public PrimaryKeyRedisCommandFabric(RedisCommandMnemonic mnemonic, RedisCommandType service, RedisCommandFamily connection) {
        this.mnemonic = mnemonic;
        this.type = service;
        this.family = connection;
    }

    @Override
    public RedisCommand build(RedisObject raw) {
        return new PrimaryKeyRedisCommand(mnemonic, type, family, raw);
    }
}
