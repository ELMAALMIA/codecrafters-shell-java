package commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CatCommand implements Command {
    @Override
    public boolean execute(String[] args, CommandState state) {
        if (args.length < 2) {
            return true;
        }

        try {
            List<String> command = new ArrayList<>();
            command.add("cat");
            for (int i = 1; i < args.length; i++) {
                command.add(args[i]);
            }
            
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.directory(state.getCurrentDir());
            pb.inheritIO();
            Process process = pb.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return true;
    }
}

