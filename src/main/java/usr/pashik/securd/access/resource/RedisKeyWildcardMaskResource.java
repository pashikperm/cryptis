package usr.pashik.securd.access.resource;

import usr.pashik.securd.platform.access.rule.AccessResource;
import usr.pashik.securd.platform.protocol.ProtocolCommand;
import usr.pashik.securd.redis.command.RedisCommand;

/**
 * Created by pashik on 16.03.14 14:59.
 */
public class RedisKeyWildcardMaskResource implements AccessResource {
    String keyPattern;

    public RedisKeyWildcardMaskResource(String pattern) {
        this.keyPattern = pattern;
    }

    @Override
    public boolean isSatisfying(ProtocolCommand command) {
        return checkWildcard(((RedisCommand) command).getPrimaryKey(), keyPattern);
    }

    @Override
    public boolean notEnoughArguments(ProtocolCommand command) {
        return ((RedisCommand) command).getPrimaryKey() == null;
    }

    public static boolean checkWildcard(String text, String pattern) {
        String[] cards = pattern.split("\\*");
        for (String card : cards) {
            int idx = text.indexOf(card);
            if (idx == -1) return false;
            text = text.substring(idx + card.length());
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("RedisKeyWildcardMaskResource [pattern=%s]", keyPattern);
    }
}
