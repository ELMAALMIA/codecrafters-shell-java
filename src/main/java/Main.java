import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        // TODO: Uncomment the code below to pass the first stage

        boolean validate = false;
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.print("$ ");
            String  cmd = scanner.nextLine();

            String cmdtab[] =cmd.split(" ");
            if(cmdtab[0].equals("echo")){
                validate = true;
                for (int i = 1; i < cmdtab.length; i++) {
                    System.out.print(cmdtab[i] + " ");
                }
                System.out.println();
            }
            if(cmd.equals("exit")){
                break;
            }
            if (!validate){
                System.out.println(cmd+": command not found");
            }
            validate=false;

        }

    }
}
