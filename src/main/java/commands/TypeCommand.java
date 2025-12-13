package commands;

import java.io.File;

public class TypeCommand implements Command {
    @Override
    public boolean execute(String[] args, CommandState state) {
        if (args.length < 2) {
            System.out.println("type: missing argument");
            return true;
        }

        if (state.getBuiltins().contains(args[1])) {
            System.out.println(args[1] + " is a shell builtin");
        } else {
            boolean found = false;
            if (state.getPathEnv() != null) {
                for (String dir : state.getDirectories()) {
                    File file = new File(dir, args[1]);
                    if (file.exists() && file.canExecute()) {
                        System.out.println(args[1] + " is " + file.getAbsolutePath());
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                System.out.println(args[1] + ": not found");
            }
        }
        
        return true;
    }
}

