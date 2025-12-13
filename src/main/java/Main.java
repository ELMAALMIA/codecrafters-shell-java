import commands.Command;
import commands.CommandFactory;
import commands.CommandParser;
import commands.CommandState;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        CommandState state = new CommandState();
        CommandFactory factory = new CommandFactory();
        CommandParser parser = new CommandParser();

        while (true) {
            System.out.print("$ ");
            String cmd = scanner.nextLine();
            String[] cmdTab = parser.parse(cmd);

            if (cmdTab.length == 0 || cmdTab[0].isEmpty()) {
                continue;
            }

            Command command = factory.getCommand(cmdTab[0]);
            boolean shouldContinue = command.execute(cmdTab, state);
            
            if (!shouldContinue) {
                break;
            }
        }
        
        scanner.close();
    }
}