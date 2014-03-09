package usr.pashik.securd.redis.protocol;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by pashik on 09.03.14 19:24.
 */
public class RESPClient extends RESPProtocol {
    Socket socket;
    RESPInputStream inputStream;
    RESPOutputStream outputStream;

    public RESPClient() throws IOException {
        super();
        socket = new Socket("localhost", RESPUtil.DEFAULT_PORT);
        buildStreams(socket);
    }

    public RESPClient(Socket socket) throws IOException {
        super();
        this.socket = socket;
        buildStreams(socket);
    }

    private void buildStreams(Socket socket) throws IOException {
        inputStream = new RESPInputStream(socket.getInputStream());
        outputStream = new RESPOutputStream(socket.getOutputStream());
    }

    public RESPInputStream getInput() {
        return inputStream;
    }

    public RESPOutputStream getOutput() {
        return outputStream;
    }

    public Socket getSocket() {
        return socket;
    }
}
