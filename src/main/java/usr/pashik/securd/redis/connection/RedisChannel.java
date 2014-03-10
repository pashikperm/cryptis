package usr.pashik.securd.redis.connection;

import usr.pashik.securd.redis.protocol.RedisInputStream;
import usr.pashik.securd.redis.protocol.RedisOutputStream;
import usr.pashik.securd.redis.protocol.RedisProtocol;
import usr.pashik.securd.redis.protocol.RedisUtil;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by pashik on 10.03.14 0:33.
 */
public class RedisChannel extends RedisProtocol {
    public RedisChannel() throws IOException {
        super();
        socket = new Socket("localhost", RedisUtil.DEFAULT_PORT);
        buildStreams(socket);
    }

    public RedisChannel(Socket socket) throws IOException {
        super();
        this.socket = socket;
        buildStreams(socket);
    }

    private void buildStreams(Socket socket) throws IOException {
        inputStream = new RedisInputStream(socket.getInputStream());
        outputStream = new RedisOutputStream(socket.getOutputStream());
    }

    public RedisInputStream getInput() {
        return inputStream;
    }

    public RedisOutputStream getOutput() {
        return outputStream;
    }

    public Socket getSocket() {
        return socket;
    }
}
