package usr.pashik.securd.protocol;

import usr.pashik.securd.protocol.commands.RedisCommand;
import usr.pashik.securd.protocol.commands.RedisParametrizedCommand;
import usr.pashik.securd.protocol.exceptions.ProtocolParseException;
import usr.pashik.securd.protocol.exceptions.ProtocolWriteException;

import java.io.IOException;


/**
 * Created by pashik on 09.03.14 16:19.
 * Thanks to Jedis!
 */
class RESPProtocol {
    public void sendCommand(RESPOutputStream out,
                            RedisCommand command) throws IOException {
        try {
            out.writeObject(command.protocolRepresentation());
        } catch (ProtocolWriteException e) {
            e.printStackTrace();
        }
    }

    public void sendParametrizedCommand(RESPOutputStream out,
                                        RedisParametrizedCommand command, Object... args) throws IOException {
        try {
            out.writeObject(command.protocolRepresentation(args));
        } catch (ProtocolWriteException e) {
            e.printStackTrace();
        }
    }

    public Object readResponce(RESPInputStream in) throws IOException {
        try {
            return in.readObject();
        } catch (ProtocolParseException e) {
            e.printStackTrace();
            return null;
        }
    }


}
