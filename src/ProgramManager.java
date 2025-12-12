import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProgramManager {
    private HashMap<String, Singer> songToSinger;
    private HashMap<Integer, Singer> singer;
    private int nextOrderNum;
    private Scanner scanner;
    private static final int NUM_TRIES_BEFORE_ERROR = 3;

    private enum Action {
        ORDER,
        REPORT,
        DONE,
        SUMMARY,
        INVALID_ACTION
    }

    public ProgramManager(HashMap<String, Singer> songToSinger, HashMap<Integer, Singer> singer) {
        this.songToSinger = songToSinger;
        this.singer = singer;
        this.nextOrderNum = 1;
        this.scanner = new Scanner(System.in);
    }

    private Action getAction(int choice){
        return switch (choice) {
            case 1 -> Action.ORDER;
            case 2 -> Action.REPORT;
            case 3 -> Action.DONE;
            case 4 -> Action.SUMMARY;
            default -> Action.INVALID_ACTION;
        };
    }

    public void selectAction(int action) {
        Action act = getAction(action);
        switch (act) {
            case ORDER:
                // select song
                System.out.println("Order a song for Valentine’s Day...\n" +
                        "Select the song from this list:\n");
                int song = selectSong();
                if (song <= 0){
                    break;
                }

                // enter email
                String email = null;
                for (int i = 0; i < NUM_TRIES_BEFORE_ERROR; i++){
                    System.out.println("Enter your email address: ");
                     email = scanner.nextLine();

                    if (checkEmailValidity(email)){
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter a valid email.");
                    }
                }
                if (email == null) {
                    System.out.println("Too many invalid inputs. Taking you back to the menu...");
                    break;
                }

                // enter credit card number
                int cc = -1;
                for (int i =  0; i < NUM_TRIES_BEFORE_ERROR; i++){
                    System.out.println("Enter your credit card number: ");
                    String line = scanner.nextLine().trim();
                    if (line.length() != 16){
                        System.out.println("Please enter a valid credit card number.");
                        continue;
                    }
                    try {
                        cc = Integer.parseInt(line);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid credit card number.");
                    }
                }
                if (cc == -1) {
                    System.out.println("Too many invalid inputs. Taking you back to the menu...");
                    break;
                }

                // enter sweetheart name
                System.out.println("Enter your sweetheart’s name: ");
                String name = scanner.nextLine();

                // make order
                boolean ret = placeOrder(song, email, cc, name);
                if (ret){
                    System.out.println("Order completed!");
                } else {
                    System.out.println("Order failed!");
                }
                break;
            case REPORT:
                System.out.println("Get a report of requests for your song...\n" +
                        "Enter your member ID number: \n");

                String report = generateReport( getMemberId(scanner));
                System.out.println(report);
                break;
            case DONE:
                System.out.println("Report back that your songs are done...\n" +
                        "Enter your member ID number: \n");
                boolean ret2 = singerCheckout(getMemberId(scanner));

               if (ret2){
                   System.out.println("Report completed!");
               } else {
                   System.out.println("Report failed!");
               }
                break;
            case SUMMARY:
                System.out.println(singer.values());
                break;
            default:
                System.out.println("Invalid action");
        }
    }

    private int getMemberId(Scanner scanner) {
        int memberId2;
        while (true) {
            System.out.print("Enter member ID: ");
            String line = scanner.nextLine().trim();
            try {
                memberId2 = Integer.parseInt(line);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return memberId2;
    }

    private int selectSong(){
        ArrayList<String> availableSongs = new ArrayList<>();
        int i = 1;
        for (String song : songToSinger.keySet()) {
            if (songToSinger.get(song).isAvailable()){
                availableSongs.add(song);
                System.out.println(i + " - " + song);
                i++;
            }
        }
        if (availableSongs.isEmpty()) {
            System.out.println("No songs available!");
            return 0;
        }

        System.out.print("Your choice (1-" + availableSongs.size() + "): ");
        int input = -1;
        for (int j = 0; j < NUM_TRIES_BEFORE_ERROR; j++) {
            try {
                input = Integer.parseInt(scanner.nextLine().strip());
                if (input > availableSongs.size() || input < 1) {
                    System.out.println("Invalid choice. Please choose a number between 1 and " + availableSongs.size() + ": ");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number number between 1 and " + availableSongs.size() + ": ");
            }
        }
        if (input == -1){
            System.out.println("Too many invalid inputs. Taking you back to the menu...");
        }
        return input;
    }

    private boolean placeOrder(int songNum, String email, int cardNum, String sweetheartName) {
        String songName = "";
        Singer singer = null;
        for (String song : this.songToSinger.keySet()) {
            singer = this.songToSinger.get(song);
            if (songNum == 1 && singer.isAvailable()) {
                songName = singer.getSongName();
                break;
            }
            if (singer.isAvailable()) {
                songNum--;
            }
        }

        if (singer == null) {
            return false;
        }

        Order newOrder = new Order(nextOrderNum++, email, cardNum, sweetheartName, songName);
        singer.addOrder(newOrder);
        return true;
    }

    private String generateReport(int singerID){
        Singer s = singer.get(singerID);
        return s.generateEmailReport();
    }

    private boolean singerCheckout(int singerID) {
        Singer s = singer.get(singerID);
        return s.finishOrders();
    }

    public Scanner getScanner() {
        return this.scanner;
    }

    private boolean checkEmailValidity(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern p = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        return email != null && p.matcher(email).matches();
    }

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
