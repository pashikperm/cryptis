package usr.pashik.securd.platform.commandengine.servercommand;

import usr.pashik.securd.bean.BeanedRunner;
import usr.pashik.securd.platform.commandengine.ServerCommand;

import java.io.IOException;
import java.net.URL;
import java.util.jar.Manifest;

/**
 * Created by pashik on 10.03.14 14:44.
 */
public class VersionServerCommand extends ServerCommand {

    @Override
    public String getName() {
        return "version";
    }

    @Override
    public String execute(String[] args) {
        try {
            Class clazz = BeanedRunner.class;
            String className = clazz.getSimpleName() + ".class";
            String classPath = clazz.getResource(className).toString();
            if (!classPath.startsWith("jar")) {
                return "ERR: can't find manifest";
            }
            String manifestPath = classPath.substring(0, classPath.lastIndexOf("!") + 1) +
                    "/META-INF/MANIFEST.MF";
            Manifest manifest = new Manifest(new URL(manifestPath).openStream());
            return String.format("Version [version=%s, created=%s, time=%s]",
                                 manifest.getMainAttributes().getValue("Build-Version"),
                                 manifest.getMainAttributes().getValue("Created-By"),
                                 manifest.getMainAttributes().getValue("Build-Time"));
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
