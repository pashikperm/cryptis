package usr.pashik.securd.access.provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import usr.pashik.securd.access.exception.UnknownCommandSetException;
import usr.pashik.securd.access.rule.RedisAccessBundle;
import usr.pashik.securd.platform.access.mode.AccessMode;
import usr.pashik.securd.platform.access.provider.AccessBundleProvider;
import usr.pashik.securd.platform.access.rule.AccessBundle;
import usr.pashik.securd.platform.configurator.ConfiguratorService;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by pashik on 16.03.14 14:24.
 */
public class FileAccessBundleProvider extends AccessBundleProvider {
    @Inject
    ConfiguratorService configuratorService;

    File bundleFile;
    AccessMode baseAccessMode;

    Logger log = LogManager.getLogger(FileAccessBundleProvider.class);

    @Override
    public Map<String, AccessBundle> reFetchAllBundles() {
        reInjectFile();

        Map<String, AccessBundle> result = new HashMap<>();
        try {
            Scanner input = new Scanner(bundleFile);
            while (input.hasNextLine()) {
                String line = input.nextLine().trim();
                if (line.length() == 0 || line.startsWith("#")) continue;
                if (isUserIdMark(line)) {
                    String userId = readUserId(line);
                    AccessBundle bundle = readBundleRules(input);
                    result.put(userId, bundle);
                }
                if (isBaseAccessModeMark(line)) {
                    readBaseAccessMode(line);
                }
            }
        } catch (Exception e) {
            log.error("Access rules file read error", e);
        }
        return result;
    }

    private void readBaseAccessMode(String line) {
        line = line.trim().toUpperCase().substring(1);
        baseAccessMode = AccessMode.valueOf(line);
    }

    private boolean isBaseAccessModeMark(String line) {
        return line.startsWith("!");
    }

    private AccessBundle readBundleRules(Scanner input) throws UnknownCommandSetException, IOException {
        RedisAccessBundle result = new RedisAccessBundle();
        RedisAccessRuleReader reader = new RedisAccessRuleReader();
        while (input.hasNextLine()) {
            String rule = input.nextLine().trim();
            if (rule.startsWith("#")) continue;
            if (rule.length() == 0) break;
            reader.readRule(rule, result);
        }
        return result;
    }

    public boolean isUserIdMark(String line) {
        return line.startsWith("[") && line.endsWith("]");
    }

    public String readUserId(String line) {
        return line.substring(1, line.length() - 1);
    }

    @Override
    public AccessMode getBaseAccessMode() {
        return baseAccessMode;
    }

    public void reInjectFile() {
        if (bundleFile == null) {
            bundleFile = new File(configuratorService.getAccessRulesFilename());
        }
    }


}
