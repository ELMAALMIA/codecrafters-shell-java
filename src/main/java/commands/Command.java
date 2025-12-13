package commands;

public interface Command {
    boolean execute(String[] args, CommandState state);
}

