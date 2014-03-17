package usr.pashik.securd.platform.userbase.provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import usr.pashik.securd.platform.auth.AuthCredentialsSerializer;
import usr.pashik.securd.platform.configurator.ConfiguratorService;
import usr.pashik.securd.platform.userbase.UserInfo;

import javax.inject.Inject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by pashik on 10.03.14 17:52.
 */
public class FileUserbaseProvider extends UserbaseProvider {
    @Inject
    ConfiguratorService configuratorService;

    File userbaseFile;
    Map<String, UserInfo> userbase;

    Logger log = LogManager.getLogger(FileUserbaseProvider.class);

    @Override
    public Map<String, UserInfo> reFetchAllUsers() {
        userbase = reReadFile();
        return userbase;
    }

    @Override
    public boolean haveUser(UserInfo user) {
        return userbase.containsKey(user.id);
    }

    @Override
    public void updateUser(UserInfo user) {
        userbase.put(user.id, user);
        reWriteFile();
    }

    @Override
    public int priority() {
        return 1;
    }

    public Map<String, UserInfo> reReadFile() {
        reInjectFile();
        userbase = new HashMap<>();
        try {
            Scanner input = new Scanner(userbaseFile);
            while (input.hasNextLine()) {
                try {
                    String authString = input.nextLine();
                    String userId = AuthCredentialsSerializer.getUserId(authString);
                    String password = AuthCredentialsSerializer.getPassword(authString);
                    String secretKey = AuthCredentialsSerializer.getSecretKey(authString);

                    userbase.put(userId, new UserInfo(userId, password, secretKey));
                } catch (Exception e) {
                    log.error("File userbase read error", e);
                }
            }
        } catch (FileNotFoundException e) {
            log.error("Userbase file not found", e);
        }
        return userbase;
    }

    public void reWriteFile() {
        reInjectFile();
        PrintWriter output;
        try {
            output = new PrintWriter(userbaseFile);
            for (UserInfo userInfo : userbase.values()) {
                output.println(AuthCredentialsSerializer.getAuthLine(userInfo));
            }
            output.close();
        } catch (FileNotFoundException e) {
            log.error("Userbase file write", e);
        }
    }

    public void reInjectFile() {
        if (userbaseFile == null) {
            userbaseFile = new File(configuratorService.getUserbaseFilename());
        }
    }
}
