import java.util.ArrayList;

public class Singer {
    private String name;
    private final String songName;
    private ArrayList<Order> singOrders;
    private boolean isAvailable;

    public Singer(String name, String songName) {
        this.name = name;
        this.songName = songName;
        this.singOrders = new ArrayList<Order>();
        this.isAvailable = true;
    }

    public String generateEmailReport() {
        // TODO: generate report + return
        if (singOrders.isEmpty()) {
            return "No Orders";
        }
        isAvailable = false;
        return singOrders.toString();
    }

    public boolean addOrder(Order o){
        if (singOrders.contains(o)){
            return false;
        }
        singOrders.add(o);
        return true;
    }

    public boolean finishOrders() {
        if (singOrders.isEmpty()){
            return false;
        }
        for (Order order : singOrders) {
            order.chargeCard();
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

    @Override
    public String toString() {
        return "[Name: " + name + ", SongName: " + songName +  " NumOrders: "+ singOrders.size() + " isAvailable: " + isAvailable + "]";
    }
}
