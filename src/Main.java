import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        HashMap<String, Singer> songToSinger = new HashMap<String, Singer>();
        HashMap<Integer, Singer> singer = new HashMap<Integer, Singer>();

        //TODO: initialize HashMaps with values

        ProgramManager pm = new ProgramManager(songToSinger, singer);

        // TODO: program logic, structure, etc.
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Jabbering Jen's Music Club Valentine's Song System!");
        System.out.println("""
                Select an action:
                1 - Customers – Order a song for Valentine’s Day
                2 – Club members – Get a report of requests for your song
                3 – Club members – Report back that your songs are done
                4 - Admin - Show data for all club members
                Enter 1, 2, 3 or 4:
                """);

        // Main I/O selecting logic

        while(true){
            // TODO add try catch
            int input = Integer.parseInt(scanner.nextLine().strip());
            if (input > 4 || input < 1){
                System.out.println("Invalid input, please try again");
            } else {
                scanner.close();
                pm.selectAction(input);
                break;
            }
        }
    }
}   