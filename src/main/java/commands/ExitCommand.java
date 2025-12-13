package commands;

public class ExitCommand implements Command {
    @Override
    public boolean execute(String[] args, CommandState state) {
        return false;
    }
}

