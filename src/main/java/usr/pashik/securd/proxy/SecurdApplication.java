package usr.pashik.securd.proxy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import usr.pashik.securd.platform.configurator.ConfiguratorService;
import usr.pashik.securd.platform.thread.InjectedRunnable;
import usr.pashik.securd.proxy.clientprocessor.ClientProcessorFabric;
import usr.pashik.securd.redis.exception.RedisAuthException;
import usr.pashik.securd.redis.exception.RedisProtocolReadException;
import usr.pashik.securd.redis.exception.RedisProtocolWriteException;

import javax.inject.Inject;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by pashik on 10.03.14 13:44.
 */
public class SecurdApplication {
    @Inject
    ConfiguratorService config;
    @Inject
    ClientProcessorFabric clientProcessorFabric;

    Logger log = LogManager.getLogger(SecurdRunner.class);

    public void start() throws IOException, RedisProtocolReadException, RedisAuthException, RedisProtocolWriteException {
        ServerSocket serverSocket = new ServerSocket(config.getProxyPort());
        log.info(String.format("Start proxy [acceptingPort=%d, serverHost=%s, serverPort=%d]",
                               config.getProxyPort(),
                               config.getServerHost(),
                               config.getServerPort()));

        ThreadGroup clientThreads = new ThreadGroup("clientProcessor");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            log.info(String.format("Accepted client [host=%s, localPort=%d]",
                                   clientSocket.getInetAddress(),
                                   clientSocket.getLocalPort()));

            InjectedRunnable clientProcessor = clientProcessorFabric.build(clientSocket);
            new Thread(clientThreads, clientProcessor).start();
        }
    }

}
