package commands;

import java.io.File;
import java.io.IOException;

public class CdCommand implements Command {
    @Override
    public boolean execute(String[] args, CommandState state) {
        if (args.length < 2) {
            System.out.println("cd: missing argument");
            return true;
        }

        String targetPath = args[1];
        if (targetPath.equals("~")) {
            String home = System.getenv("HOME");
            if (home == null) {
                home = System.getProperty("user.home");
            }
            targetPath = home;
        } else if (targetPath.startsWith("~/")) {
            String home = System.getenv("HOME");
            if (home == null) {
                home = System.getProperty("user.home");
            }
            targetPath = home + targetPath.substring(1);
        }

        File newDir = new File(targetPath);

        if (!newDir.isAbsolute()) {
            newDir = new File(state.getCurrentDir(), targetPath);
        }

        if (newDir.exists() && newDir.isDirectory()) {
            try {
                state.setCurrentDir(newDir.getCanonicalFile());
            } catch (IOException e) {
                System.out.println("cd: " + args[1] + ": No such file or directory");
            }
        } else {
            System.out.println("cd: " + args[1] + ": No such file or directory");
        }
        
        return true;
    }
}

