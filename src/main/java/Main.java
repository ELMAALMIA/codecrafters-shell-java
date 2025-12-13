import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        // TODO: Uncomment the code below to pass the first stage
        System.out.print("$ ");
        boolean validate = false;
        Scanner scanner = new Scanner(System.in);
        String  cmd = scanner.nextLine();
        if (!validate){
            System.out.print(cmd+": command not found");
        }
    }
}
