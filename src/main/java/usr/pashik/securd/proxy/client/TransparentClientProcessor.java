package usr.pashik.securd.proxy.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import usr.pashik.securd.platform.configurator.ConfiguratorService;
import usr.pashik.securd.platform.thread.InjectedRunnable;
import usr.pashik.securd.redis.command.RedisCommand;
import usr.pashik.securd.redis.connection.RedisChannel;
import usr.pashik.securd.redis.protocol.response.RedisObject;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Created by pashik on 06.03.14 23:47.
 */
public class TransparentClientProcessor extends InjectedRunnable {
    @Inject
    ConfiguratorService config;

    RedisChannel client;
    RedisChannel server;

    Logger log = LogManager.getLogger(TransparentClientProcessor.class);

    @Override
    public void runInjected() {
        try {
            while (true) {
                RedisCommand command = client.readCommand();
                server.sendCommand(command);
                RedisObject response = server.readResponse();
                client.sendResponse(response);
            }
        } catch (IOException e) {
            log.error(e);
        }
    }
}
