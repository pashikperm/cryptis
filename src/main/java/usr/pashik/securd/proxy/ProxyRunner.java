package usr.pashik.securd.proxy;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.events.ContainerInitialized;
import usr.pashik.securd.configurator.ConfiguratorService;
import usr.pashik.securd.platform.LogService;
import usr.pashik.securd.protocol.RESPClient;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by pashik on 06.03.14 23:43.
 */
public class ProxyRunner {
    @Inject
    ConfiguratorService config;

    @Inject
    LogService logService;

//    Logger log;

    public static void main(String[] args) {
        Weld weld = new Weld();
        weld.initialize();
        weld.shutdown();
    }

    public void start(@Observes ContainerInitialized initialized) throws IOException {
        ServiceActivator.activateServices();

//        log = logService.getLogger(this);

        ServerSocket serverSocket = new ServerSocket(config.getProxyPort());
//        log.info(String.format("Start proxy [acceptingPort=%d, serverHost=%s, serverPort=%d]", config.getProxyPort(), config.getServerHost(), config.getServerPort()));

        while (true) {
            Socket clientSocket = serverSocket.accept();
//            log.info(String.format("Accepted client [host=%s, localPort=%d]", clientSocket.getInetAddress(), clientSocket.getLocalPort()));

            RESPClient client = new RESPClient(clientSocket);
            RESPClient server = new RESPClient();
            new Thread(new ClientProcessor(client, server)).start();
        }
    }
}

