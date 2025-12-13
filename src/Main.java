import java.util.HashMap;
import java.util.Scanner;

public class Main {

    // Mason & PJ
    public static void main(String[] args) {
        HashMap<String, Singer> songToSinger = new HashMap<String, Singer>();
        HashMap<Integer, Singer> singer = new HashMap<Integer, Singer>();

        // populate HashMaps with sample data
        Singer s1 = new Singer(1, "Mason", "\"Can't Help Falling in Love\" by Elvis Presley");
        Singer s2 = new Singer(2, "PJ", "\"At Last\" by Etta James");
        Singer s3 = new Singer(3, "Niko", "\"Ballad of a homeschooled girl\" by Olivia Rodrigo");
        songToSinger.put(s1.getSongName(), s1);
        songToSinger.put(s2.getSongName(), s2);
        songToSinger.put(s3.getSongName(), s3);
        singer.put(s1.getId(), s1);
        singer.put(s2.getId(), s2);
        singer.put(s3.getId(), s3);

        ProgramManager pm = new ProgramManager(songToSinger, singer);
        Scanner scanner = pm.getScanner();

        // Main I/O selecting logic
        while(true){
            System.out.println("Welcome to the Jabbering Jen's Music Club Valentine's Song System!");
            System.out.println("""
                Select an action:
                1 - Customers – Order a song for Valentine’s Day
                2 – Club members – Get a report of requests for your song
                3 – Club members – Report back that your songs are done
                4 - Admin - Show data for all club members
                Enter 1, 2, 3 or 4:  """);
            String line = scanner.nextLine().trim();
            if (line.equals("exit")) {
                break;
            }

            int input = -1;
            try {
                input = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                continue;
            }

            if (input > 4 || input < 1){
                System.out.println("Invalid input, please try again");
            } else {
                pm.selectAction(input);
                System.out.println();
            }
        }
    }
}
