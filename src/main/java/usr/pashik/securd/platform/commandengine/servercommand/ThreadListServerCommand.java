package usr.pashik.securd.platform.commandengine.servercommand;

import usr.pashik.securd.platform.commandengine.ServerCommand;

import java.util.Set;

/**
 * Created by pashik on 10.03.14 23:56.
 */
public class ThreadListServerCommand extends ServerCommand {
    @Override
    public String getName() {
        return "threadlist";
    }

    @Override
    public String execute(String[] args) {
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        StringBuilder result = new StringBuilder("Thread list:\n");
        for (Thread thread : threadSet) {
            result.append(String.format("[group=%30s, name=%30s, id=%s]\n",
                                        thread.getThreadGroup().getName(),
                                        thread.getName(),
                                        thread.getId()));
        }
        return result.toString();
    }
}
