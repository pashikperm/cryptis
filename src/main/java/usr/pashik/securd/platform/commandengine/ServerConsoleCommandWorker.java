package usr.pashik.securd.platform.commandengine;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import usr.pashik.securd.platform.thread.InjectedRunnable;

import javax.inject.Inject;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by pashik on 10.03.14 1:24.
 */
public class ServerConsoleCommandWorker extends InjectedRunnable {
    @Inject
    ServerCommandService commandEngine;

    Scanner input = new Scanner(System.in);
    PrintWriter output = new PrintWriter(System.out);

    Logger log = LogManager.getLogger(ServerConsoleCommandWorker.class);

    @Override
    public void runInjected() {
        while (true) {
            try {
                String command = readCommand();
                executeCommand(command);
                writeWelcome(true);
            } catch (Exception e) {
                log.error("Command error", e);
                writeWelcome(true);
            }
        }
    }

    private void executeCommand(String command) throws Exception {
        String commandResult = commandEngine.parseAndExecuteCommand(command);
        output.print(commandResult);
        output.flush();
    }

    private String readCommand() {
        String command;
        while ((command = input.nextLine()).length() == 0) {
            writeWelcome(false);
        }
        return command;
    }

    private void writeWelcome(boolean addPrefixLine) {
        output.print(addPrefixLine ? "\nproxy> " : "proxy> ");
        output.flush();
    }
}
