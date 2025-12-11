import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class ProgramManager {
    private HashMap<String, Singer> songToSinger;
    private HashMap<Integer, Singer> singer;
    private int nextOrderNum;

    public ProgramManager(HashMap<String, Singer> songToSinger, HashMap<Integer, Singer> singer) {
        this.songToSinger = songToSinger;
        this.singer = singer;
        this.nextOrderNum = 1;
    }

    public void selectAction(int action) {
        Scanner scanner = new Scanner(System.in);
        switch (action) {
            case 1:
                System.out.println("Order a song for Valentine’s Day...\n" +
                        "Select the song from this list:\n");
                int song = listSong();
                if (song <=0){
                    break;
                }
                System.out.println("Enter your email address: ");
                String email = scanner.nextLine();
                int cc;
                while (true) {
                    System.out.println("Enter your credit card number: ");
                    String line = scanner.nextLine().trim();

                    try {
                        cc = Integer.parseInt(line);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                    }
                }

                System.out.println("Enter your sweetheart’s name: ");
                String name = scanner.nextLine();
                boolean ret = placeOrder(song, email, cc, name);
                if (ret){
                    System.out.println("Order completed!");
                } else {
                    System.out.println("Order failed!");
                }

                break;
            case 2:
                System.out.println("Get a report of requests for your song...\n" +
                        "Enter your member ID number: \n");

                String report = generateReport( getMemberId(scanner));
                System.out.println(report);
                break;
            case 3:
                System.out.println("Report back that your songs are done...\n" +
                        "Enter your member ID number: \n");
                boolean ret2 = singerCheckout(getMemberId(scanner));

               if (ret2){
                   System.out.println("Report completed!");
               } else {
                   System.out.println("Report failed!");
               }
                break;
            case 4:
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

    private int listSong(){
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
        Scanner scanner = new Scanner(System.in);
        System.out.print("Your choice (1-" + availableSongs.size() + "): ");
        int input;
        while (true) {
            // TODO add try catch
            input = Integer.parseInt(scanner.nextLine().strip());
            if (input > availableSongs.size() || input < 1) {
                System.out.println("Invalid choice");
            } else {
                break;
            }
        }
        return input;
    }
    private boolean placeOrder(int songNum, String email, int cardNum, String sweetheartName) {
        String songName = "";
        Singer s = null;
        for (String song : this.songToSinger.keySet()) {
            s = this.songToSinger.get(song);
            if (songNum == 1 && s.isAvailable()) {
                songName = s.getSongName();
                break;
            }
            if (s.isAvailable()) {
                songNum--;
            }
        }

        if (s == null) {
            return false;
        }
        // TODO: can add data validation
        Order newOrder = new Order(nextOrderNum++, email, cardNum, sweetheartName, songName);
        s.addOrder(newOrder);
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

    private enum Action {
        ORDER,
        REPORT,
        DONE,
        SUMMARY
    }
}
