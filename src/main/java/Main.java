import java.io.File;
import java.io.IOException;
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

        label:
        while (true) {
            System.out.print("$ ");
            String cmd = scanner.nextLine();
            String[] cmdTab = cmd.split(" ");

            switch (cmdTab[0]) {
                case "exit":
                    break label;
                case "pwd":

                    System.out.println(new File("").getAbsolutePath());
                    break;
                case "echo":
                    for (int i = 1; i < cmdTab.length; i++) {
                        if (i > 1) System.out.print(" ");
                        System.out.print(cmdTab[i]);
                    }
                    System.out.println();
                    break;
                case "type":
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
                    break;
                default:
                    String pathExe = "";
                    for (String dir : directories) {
                        File file = new File(dir, cmdTab[0]);
                        if (file.exists() && file.canExecute()) {
                            pathExe = file.getAbsolutePath();
                            break;
                        }
                    }
                    if (!pathExe.isEmpty()) {
                        try {
                            ProcessBuilder pb = new ProcessBuilder(cmdTab);
                            pb.inheritIO();
                            Process process = pb.start();
                            process.waitFor();
                        } catch (IOException | InterruptedException e) {
                            System.out.println("Error " + e.getMessage());
                        }
                    } else {
                        System.out.println(cmdTab[0] + ": command not found");
                    }
                    break;
            }
        }
    }
}