import java.io.File;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String pathEnv = System.getenv("PATH");
        String[] directories = {};
        if (pathEnv != null) {
            directories = pathEnv.split(":");
        }
        Set<String> builtins = Set.of("exit", "echo", "type");

        while (true) {
            System.out.print("$ ");
            String cmd = scanner.nextLine();
            String[] cmdTab = cmd.split(" ");

            if (cmdTab[0].equals("exit")) {
                break;
            } else if (cmdTab[0].equals("echo")) {
                for (int i = 1; i < cmdTab.length; i++) {
                    if (i > 1) System.out.print(" ");
                    System.out.print(cmdTab[i]);
                }
                System.out.println();
            } else if (cmdTab[0].equals("type")) {
                if (builtins.contains(cmdTab[1])) {
                    System.out.println(cmdTab[1] + " is a shell builtin");
                } else {
                    boolean found = false;
                    if (pathEnv != null) {
                        for (String dir : directories) {
                            File file = new File(dir, cmdTab[1]);
                            if (file.exists() && file.canExecute()) {
                                System.out.println(cmdTab[1] + " is " + file.getAbsolutePath());
                                found = true;
                                break;
                            }
                        }
                    }
                    if (!found) {
                        System.out.println(cmdTab[1] + ": not found");
                    }
                }
            } else {
                System.out.println(cmdTab[0] + ": command not found");
            }
        }
    }
}