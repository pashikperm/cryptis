package usr.pashik.securd.redis.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.weld.environment.se.events.ContainerInitialized;
import usr.pashik.securd.redis.command.RedisCommandService;
import usr.pashik.securd.redis.command.info.RedisCommandFamily;
import usr.pashik.securd.redis.command.info.RedisCommandMnemonic;
import usr.pashik.securd.redis.command.info.RedisCommandType;
import usr.pashik.securd.redis.command.meta.RedisCommandFabric;
import usr.pashik.securd.redis.command.meta.fabric.NoKeyRedisCommandFabric;
import usr.pashik.securd.redis.command.meta.fabric.PrimaryKeyRedisCommandFabric;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

/**
 * Created by pashik on 16.03.14 13:53.
 */
public class FileCommandSet {
    @Inject
    RedisCommandService commandService;

    Logger log = LogManager.getLogger(FileCommandSet.class);

    final String COMMAND_FILE_PATH = "/commandset.txt";


    public void onInitialized(@Observes ContainerInitialized initialized) {
        try {
            Scanner input = new Scanner(FileCommandSet.class.getResourceAsStream(COMMAND_FILE_PATH));
            while (input.hasNextLine()) {
                String command = input.nextLine();
                if (command.length() == 0) continue;
                processCommandLine(command);
            }
        } catch (Exception e) {
            log.trace(e.getMessage(), e);
        }
    }

    private void processCommandLine(String command) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        if (command.startsWith("#")) return;
        command = command.toUpperCase();

        String[] tokens = command.split(" ");

        String rawMnemonic = tokens[0];
        String rawType = tokens[1];
        String rawFamily = tokens[2];
        String rawClazz = tokens[3];

        Class<? extends RedisCommandFabric> clazz = getCommandFabric(rawClazz);

        Constructor<? extends RedisCommandFabric> fabricConstructor = clazz.getConstructor(new Class[]{
                RedisCommandMnemonic.class,
                RedisCommandType.class,
                RedisCommandFamily.class});

        RedisCommandMnemonic mnemonic = RedisCommandMnemonic.valueOf(rawMnemonic);
        RedisCommandType type = RedisCommandType.valueOf(rawType);
        RedisCommandFamily family = RedisCommandFamily.valueOf(rawFamily);

        RedisCommandFabric fabric = fabricConstructor.newInstance(mnemonic, type, family);

        commandService.registerFabric(fabric);
    }

    private Class<? extends RedisCommandFabric> getCommandFabric(String rawClazz) {
        switch (rawClazz) {
            case "NOKEY":
                return NoKeyRedisCommandFabric.class;
            case "PRIMARY":
                return PrimaryKeyRedisCommandFabric.class;
            default:
                return null;
        }
    }

}
