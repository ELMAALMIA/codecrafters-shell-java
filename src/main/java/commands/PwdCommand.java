package commands;

public class PwdCommand implements Command {
    @Override
    public boolean execute(String[] args, CommandState state) {
        System.out.println(state.getCurrentDir().getAbsolutePath());
        return true;
    }
}

