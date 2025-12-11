import java.util.ArrayList;

public class Singer {
    private int id; // TODO: maybe we dont need this? Better to have this to double check but it can also be stored in Hashmap in Manager
    private String name;
    private final String songName;
    private ArrayList<Order> singOrders;
    private boolean isAvailable;

    public Singer(int id, String name, String songName) {
        this.id = id;
        this.name = name;
        this.songName = songName;
        this.singOrders = new ArrayList<Order>();
        this.isAvailable = true;
    }

    public String generateEmailReport() {
        // TODO: generate report + return
        isAvailable = false;
        return singOrders.toString();
    }

    public boolean addOrder(Order o){
        if (singOrders.contains(o)){
            return false;
        }
        singOrders.add(o);
        this.isAvailable = false;
        return true;
    }

    public boolean finishOrders() {
        if (singOrders.isEmpty()){
            return false;
        }
        singOrders.clear();
        this.isAvailable = true;
        return true;
    }

    public boolean isAvailable() {
        return this.isAvailable;
    }

    public String getSongName() {
        return this.songName;
    }
}
