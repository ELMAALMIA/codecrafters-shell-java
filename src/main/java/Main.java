import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        // TODO: Uncomment the code below to pass the first stage

        boolean validate = false;
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.print("$ ");
            String  cmd = scanner.nextLine();
            if(cmd.equals("exit")){
                break;
            }
            if (!validate){
                System.out.println(cmd+": command not found");
            }

        }

    }
}
