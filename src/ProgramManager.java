import java.util.ArrayList;
import java.util.HashMap;
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

}
