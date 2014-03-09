package usr.pashik.securd.platform.commands;

import javax.inject.Inject;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by pashik on 10.03.14 1:24.
 */
public class ServerConsoleCommandWorker implements Runnable {
    @Inject
    ServerCommandEngine commandEngine;

    Scanner input = new Scanner(System.in);
    PrintWriter output = new PrintWriter(System.out);

    @Override
    public void run() {
        while (true) {
            writeWelcome();
            String command = readCommand();
            executeCommand(command);
        }
    }

    private void executeCommand(String command) {
        String commandResult = commandEngine.parseAndExecuteCommand(command);
        output.print(commandResult);
        output.flush();
    }

    private String readCommand() {
        return input.nextLine();
    }

    private void writeWelcome() {
        output.print("\nproxy> ");
        output.flush();
    }
}
