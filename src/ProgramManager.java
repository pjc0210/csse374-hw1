import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ProgramManager {
    private HashMap<String, Singer> songToSinger;
    private HashMap<Integer, Singer> singer;
    private int nextOrderNum;
    private final Scanner scanner;
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
                long cc = -1;
                for (int i =  0; i < NUM_TRIES_BEFORE_ERROR; i++){
                    System.out.println("Enter your credit card number: ");
                    String line = scanner.nextLine().trim();
                    if (line.length() != 16){
                        System.out.println("Please enter a valid credit card number.");
                        continue;
                    }
                    try {
                        cc = Long.parseLong(line);
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
                String name = null;
                for (int i = 0; i < NUM_TRIES_BEFORE_ERROR; i++) {
                    System.out.println("Enter your sweetheart’s name: ");
                    name = scanner.nextLine();
                    if (name != null) {
                        break;
                    } else {
                        System.out.println("Please enter a non-empty name.");
                    }
                }

                // make order
                boolean ret = placeOrder(song, email, cc, name);
                if (ret){
                    System.out.println("Order completed!");
                } else {
                    System.out.println("Order failed!");
                }
                break;

            case REPORT:
                System.out.println("Get a report of requests for your song...");

                Singer s = getSingerFromInput();
                System.out.println(handleGenerateReport(s));
                break;

            case DONE:
                System.out.println("Report back that your songs are done...\n");
                Singer s2 = getSingerFromInput();
                int ret2 = handleSingerCheckout(s2);

                if (ret2 == 1){
                    System.out.println("Report completed!");
                } else if (ret2 == 0) {
                    System.out.println("There were no orders to complete!");
                } else if (ret2 < 0) {
                   System.out.println("Check your orders before you complete them!");
                }
                break;

            case SUMMARY:
                for (Singer sin : singer.values()) {
                    System.out.println(sin.toString());
                }
                break;

            default:
                System.out.println("Invalid action");
        }
    }

    private Singer getSingerFromInput() {
        String input = null;
        int singerID = -1;
        Singer s = null;

        for (int i = 0; i < NUM_TRIES_BEFORE_ERROR; i++){
            System.out.println("Enter your member ID number: ");
            input = scanner.nextLine().trim();
            try {
                singerID = Integer.parseInt(input);
                s = singer.get(singerID);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid member ID.");
            }
        }
        return s;
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

        System.out.print((availableSongs.size() == 1) ? "Your choice (1): " : "Your choice (1-" + availableSongs.size() + "): ");
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
        if (input > availableSongs.size() || input < 1){
            System.out.println("Too many invalid inputs. Taking you back to the menu...");
            return 0;
        }
        return input;
    }

    private boolean placeOrder(int songNum, String email, long cardNum, String sweetheartName) {
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
        return singer.addOrder(newOrder);
    }

    private String handleGenerateReport(Singer s){
        return s.generateEmailReport();
    }

    private int handleSingerCheckout(Singer s) {
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
}
