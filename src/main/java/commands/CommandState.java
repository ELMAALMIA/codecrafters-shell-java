package commands;

import java.io.File;
import java.util.Set;

public class CommandState {
    private File currentDir;
    private String pathEnv;
    private String[] directories;
    private Set<String> builtins;

    public CommandState() {
        this.currentDir = new File(System.getProperty("user.dir"));
        this.pathEnv = System.getenv("PATH");
        this.builtins = Set.of("exit", "echo", "type", "pwd", "cd");
        
        if (this.pathEnv != null) {
            this.directories = this.pathEnv.split(":");
        } else {
            this.directories = new String[0];
        }
    }

    public File getCurrentDir() {
        return currentDir;
    }

    public void setCurrentDir(File currentDir) {
        this.currentDir = currentDir;
    }

    public String getPathEnv() {
        return pathEnv;
    }

    public String[] getDirectories() {
        return directories;
    }

    public Set<String> getBuiltins() {
        return builtins;
    }
}

