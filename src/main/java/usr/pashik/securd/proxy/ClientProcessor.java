package usr.pashik.securd.proxy;

import usr.pashik.securd.protocol.RESPClient;

import java.io.IOException;

/**
 * Created by pashik on 06.03.14 23:47.
 */
public class ClientProcessor implements Runnable {
    RESPClient client;
    RESPClient server;

    public ClientProcessor(RESPClient client, RESPClient server) throws IOException {
        this.client = client;
        this.server = server;
    }

    @Override
    public void run() {
//        try {
//            while (true) {
//                RedisCommand command = client.readCommand();
//                server.sendCommand(command);
//                RESPObject responce = server.readResponce();
//                client.writeResponce(responce);
//
//                System.out.println("command = " + command + ", responce = " + responce);
//    }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
