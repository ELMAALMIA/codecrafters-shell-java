package commands;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private final Map<String, Command> commandMap;

    public CommandFactory() {
        this.commandMap = new HashMap<>();
        initializeCommands();
    }

    private void initializeCommands() {
        commandMap.put("exit", new ExitCommand());
        commandMap.put("echo", new EchoCommand());
        commandMap.put("type", new TypeCommand());
        commandMap.put("pwd", new PwdCommand());
        commandMap.put("cd", new CdCommand());
        commandMap.put("cat", new CatCommand());
    }

    public Command getCommand(String commandName) {
        Command command = commandMap.get(commandName);
        if (command != null) {
            return command;
        }
        return new ExternalCommand();
    }
}

