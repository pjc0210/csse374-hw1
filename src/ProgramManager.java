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
                System.out.println("Enter your email address: ");
                String email = scanner.nextLine();
                System.out.println("Enter your credit card number: ");
                // TODO try catch
                int creditCard = Integer.parseInt(scanner.nextLine().strip());
                System.out.println("Enter your sweetheart’s name: ");
                String name = scanner.nextLine();
                boolean ret = placeOrder(song, email, creditCard, name);
                if (ret){
                    System.out.println("Order completed!");
                } else {
                    System.out.println("Order failed!");
                }
                scanner.close();
                break;
            case 2:
                System.out.println("Get a report of requests for your song...\n" +
                        "Enter your member ID number: \n");
                int memberId = scanner.nextInt();
                String report = generateReport(memberId);
                System.out.println(report);
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                System.out.println("Invalid action");
        }
    }
    private int listSong(){
        ArrayList<String> availableSongs = new ArrayList<>();
        int i = 1;
        for (String song : songToSinger.keySet()) {
            if (songToSinger.get(song).isAvailable()){
                availableSongs.add(song);
                i++;
                System.out.println(i + " - " + song);
            }
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
                // Subtract to align input with array 0 indexing
                input--;
                break;
            }
        }
        scanner.close();
        return input;
    }
    private boolean placeOrder(int songNum, String email, int cardNum, String sweetheartName) {
        String songName = "";
        Singer s = null;
        for (String song : this.songToSinger.keySet()) {
            s = this.songToSinger.get(song);
            if (songNum == 1) {
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
