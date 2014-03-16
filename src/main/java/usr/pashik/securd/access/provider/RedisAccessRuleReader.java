package usr.pashik.securd.access.provider;

import usr.pashik.securd.access.exception.UnknownCommandSetException;
import usr.pashik.securd.access.resource.RedisKeyRegexResource;
import usr.pashik.securd.access.resource.RedisKeyWildcardMaskResource;
import usr.pashik.securd.access.rule.RedisAccessBundle;
import usr.pashik.securd.platform.access.mode.AccessMode;
import usr.pashik.securd.platform.access.rule.AccessResource;
import usr.pashik.securd.platform.access.rule.AccessRule;
import usr.pashik.securd.platform.bean.BeanedRunner;
import usr.pashik.securd.redis.command.RedisCommandService;
import usr.pashik.securd.redis.command.info.RedisCommandFamily;
import usr.pashik.securd.redis.command.info.RedisCommandMnemonic;
import usr.pashik.securd.redis.command.info.RedisCommandType;

import javax.inject.Inject;
import java.util.Arrays;

/**
 * Created by pashik on 16.03.14 17:46.
 */
public class RedisAccessRuleReader {
    @Inject
    RedisCommandService commandService;

    public RedisAccessRuleReader() {
        if (commandService == null) {
            commandService = BeanedRunner.getWeldContainer().instance().select(RedisCommandService.class).get();
        }
    }

    public void readRule(String rawRule, RedisAccessBundle result) throws UnknownCommandSetException {
        if (rawRule.startsWith("#")) return;
        String[] tokens = rawRule.split(" ");

        String rawMode = tokens[0].toUpperCase();
        String rawCommandSet = tokens[1].toUpperCase();
        String rawResource = tokens.length > 2 ? tokens[2] : null;

        AccessMode mode = AccessMode.valueOf(rawMode);
        AccessResource resource = parseResource(rawResource);

        AccessRule rule = new AccessRule(mode, resource);
        processCommandSet(result, rawCommandSet, rule);
    }

    private void processCommandSet(RedisAccessBundle result, String rawCommandSet, AccessRule rule) throws UnknownCommandSetException {
        try {
            RedisCommandType type = RedisCommandType.valueOf(rawCommandSet);
            processTypeAccessRule(result, type, rule);
            return;
        } catch (Exception e) {
        }
        try {
            RedisCommandFamily family = RedisCommandFamily.valueOf(rawCommandSet);
            processFamilyAccessRule(result, family, rule);
            return;
        } catch (Exception e) {
        }
        try {
            RedisCommandMnemonic mnemonic = RedisCommandMnemonic.valueOf(rawCommandSet);
            processMnemonicAccessRule(result, mnemonic, rule);
            return;
        } catch (Exception e) {
        }
        throw new UnknownCommandSetException();
    }

    private void processMnemonicAccessRule(RedisAccessBundle bundle, RedisCommandMnemonic mnemonic, AccessRule rule) {
        bundle.accessRules[mnemonic.ordinal()] = appendRule(bundle.accessRules[mnemonic.ordinal()], rule);
    }

    private void processFamilyAccessRule(RedisAccessBundle bundle, RedisCommandFamily family, AccessRule rule) {
        for (RedisCommandMnemonic mnemonic : RedisCommandMnemonic.values()) {
            if (commandService.getCommandFamily(mnemonic) == family) {
                bundle.accessRules[mnemonic.ordinal()] = appendRule(bundle.accessRules[mnemonic.ordinal()], rule);
            }
        }
    }

    private void processTypeAccessRule(RedisAccessBundle bundle, RedisCommandType type, AccessRule rule) {
        for (RedisCommandMnemonic mnemonic : RedisCommandMnemonic.values()) {
            if (commandService.getCommandType(mnemonic) == type) {
                bundle.accessRules[mnemonic.ordinal()] = appendRule(bundle.accessRules[mnemonic.ordinal()], rule);
            }
        }
    }

    private AccessRule[] appendRule(AccessRule[] rules, AccessRule rule) {
        if (rules == null) {
            return new AccessRule[]{rule};
        }
        AccessRule[] newRules = Arrays.copyOf(rules, rules.length + 1);
        newRules[rules.length] = rule;
        return newRules;
    }

    private static AccessResource parseResource(String rawResource) {
        if (rawResource == null) return null;
        if (rawResource.startsWith("r")) return new RedisKeyRegexResource(rawResource.substring(1));
        return new RedisKeyWildcardMaskResource(rawResource);
    }
}