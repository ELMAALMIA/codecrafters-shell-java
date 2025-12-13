package commands;

import java.io.File;
import java.io.IOException;

public class ExternalCommand implements Command {
    @Override
    public boolean execute(String[] args, CommandState state) {
        String pathExe = findExecutable(args[0], state);

        if (!pathExe.isEmpty()) {
            try {
                ProcessBuilder pb = new ProcessBuilder(args);
                pb.directory(state.getCurrentDir());
                pb.inheritIO();
                Process process = pb.start();
                process.waitFor();
            } catch (IOException | InterruptedException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println(args[0] + ": command not found");
        }
        
        return true;
    }

    private String findExecutable(String command, CommandState state) {
        for (String dir : state.getDirectories()) {
            File file = new File(dir, command);
            if (file.exists() && file.canExecute()) {
                return file.getAbsolutePath();
            }
        }
        return "";
    }
}

