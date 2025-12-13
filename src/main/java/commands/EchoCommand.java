package commands;

public class EchoCommand implements Command {
    @Override
    public boolean execute(String[] args, CommandState state) {
        for (int i = 1; i < args.length; i++) {
            if (i > 1) System.out.print(" ");
            System.out.print(args[i]);
        }
        System.out.println();
        return true;
    }
}

