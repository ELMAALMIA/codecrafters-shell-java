import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private static File currentDir = new File(System.getProperty("user.dir"));
    private static String pathEnv = System.getenv("PATH");
    private static String[] directories = {};
    private static Set<String> builtins = Set.of("exit", "echo", "type", "pwd", "cd");

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        if (pathEnv != null) {
            directories = pathEnv.split(":");
        }

        label:
        while (true) {
            System.out.print("$ ");
            String cmd = scanner.nextLine();
            String[] cmdTab = cmd.split(" ");

            switch (cmdTab[0]) {
                case "exit":
                    break label;
                case "pwd":
                    handlePwd();
                    break;
                case "cd":
                    handleCd(cmdTab);
                    break;
                case "echo":
                    handleEcho(cmdTab);
                    break;
                case "type":
                    handleType(cmdTab);
                    break;
                default:
                    handleExternalCommand(cmdTab);
                    break;
            }
        }
        scanner.close();
    }

    private static void handlePwd() {
        System.out.println(currentDir.getAbsolutePath());
    }

    private static void handleCd(String[] cmdTab) {
        if (cmdTab.length < 2) {
            System.out.println("cd: missing argument");
            return;
        }

        String targetPath = cmdTab[1];

        if (targetPath.equals("~")) {
            targetPath = System.getProperty("user.home");
        } else if (targetPath.startsWith("~/")) {
            targetPath = System.getProperty("user.home") + targetPath.substring(1);
        }


        File newDir = new File(targetPath);


        if (!newDir.isAbsolute()) {
            newDir = new File(currentDir, targetPath);
        }


        if (newDir.exists() && newDir.isDirectory()) {
            try {
                currentDir = newDir.getCanonicalFile();
            } catch (IOException e) {
                System.out.println("cd: " + cmdTab[1] + ": No such file or directory");
            }
        } else {
            System.out.println("cd: " + cmdTab[1] + ": No such file or directory");
        }
    }

    private static void handleEcho(String[] cmdTab) {
        for (int i = 1; i < cmdTab.length; i++) {
            if (i > 1) System.out.print(" ");
            System.out.print(cmdTab[i]);
        }
        System.out.println();
    }

    private static void handleType(String[] cmdTab) {
        if (cmdTab.length < 2) {
            System.out.println("type: missing argument");
            return;
        }

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
    }

    private static void handleExternalCommand(String[] cmdTab) {
        String pathExe = findExecutable(cmdTab[0]);

        if (!pathExe.isEmpty()) {
            try {
                ProcessBuilder pb = new ProcessBuilder(cmdTab);
                pb.directory(currentDir);
                pb.inheritIO();
                Process process = pb.start();
                process.waitFor();
            } catch (IOException | InterruptedException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println(cmdTab[0] + ": command not found");
        }
    }

    private static String findExecutable(String command) {
        for (String dir : directories) {
            File file = new File(dir, command);
            if (file.exists() && file.canExecute()) {
                return file.getAbsolutePath();
            }
        }
        return "";
    }
}