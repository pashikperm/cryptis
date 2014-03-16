package usr.pashik.securd.access.resource;

import usr.pashik.securd.platform.access.rule.AccessResource;
import usr.pashik.securd.platform.protocol.ProtocolCommand;
import usr.pashik.securd.redis.command.RedisCommand;

import java.util.regex.Pattern;

/**
 * Created by pashik on 16.03.14 14:25.
 */
public class RedisKeyRegexResource implements AccessResource {
    Pattern keyPattern;

    public RedisKeyRegexResource(String regex) {
        keyPattern = Pattern.compile(regex);
    }

    @Override
    public boolean isSatisfying(ProtocolCommand command) {
        return keyPattern.matcher(((RedisCommand) command).getPrimaryKey()).matches();
    }

    @Override
    public boolean notEnoughArguments(ProtocolCommand command) {
        return ((RedisCommand) command).getPrimaryKey() == null;
    }

    @Override
    public String toString() {
        return String.format("RedisKeyRegexResource [pattern=%s]", keyPattern);
    }
}
