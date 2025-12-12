import java.util.ArrayList;

public class Singer {
    private int id;
    private String name;
    private final String songName;
    private ArrayList<Order> singOrders;
    private boolean isAvailable;

    public Singer(int id, String name, String songName) {
        this.name = name;
        this.songName = songName;
        this.singOrders = new ArrayList<Order>();
        this.isAvailable = true;
        this.id = id;
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

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        String orders;
        if (singOrders.isEmpty()) {
            orders = "No Orders";
        } else {
            orders = singOrders.toString();
        }
        return "[Name: " + name + ", SongName: " + songName +  ", NumOrders: "+ singOrders.size() + ", Orders: " +  orders +", isAvailable: " + isAvailable + "]\n";
    }
}
