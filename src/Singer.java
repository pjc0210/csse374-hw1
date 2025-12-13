import java.util.ArrayList;

public class Singer {
    private int id;
    private String name;
    private final String songName;
    private ArrayList<Order> singOrders;
    private boolean isAvailable;

    // PJ
    public Singer(int id, String name, String songName) {
        this.name = name;
        this.songName = songName;
        this.singOrders = new ArrayList<Order>();
        this.isAvailable = true;
        this.id = id;
    }

    // Mason
    public String generateEmailReport() {
        if (singOrders.isEmpty()) {
            return "No Orders";
        }
        isAvailable = false;
        // should be emailed but for the purpose of this homework it is returned to be printed to console
        return this.getOrderReport();
    }

    // PJ
    public boolean addOrder(Order o){
        if (singOrders.contains(o)){
            return false;
        }
        singOrders.add(o);
        return true;
    }

    // Mason
    public int finishOrders() {
        if (singOrders.isEmpty()) {
            return 0;
        } else if (this.isAvailable) {
            return -1;
        }
        boolean ret = true;
        for (Order order : singOrders) {
            order.chargeCard();
        }

        singOrders.clear();
        this.isAvailable = true;
        return 1;
    }

    // PJ
    public boolean isAvailable() {
        return this.isAvailable;
    }

    // PJ
    public String getSongName() {
        return this.songName;
    }

    // PJ
    public int getId() {
        return this.id;
    }

    // PJ
    public String getOrderReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sweethearts to sing to: ");
        for (Order order : singOrders) {
            sb.append(order.getSweetheartName());
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append("\n");
        return sb.toString();
    }

    // Mason
    @Override
    public String toString() {
        String orders;
        if (singOrders.isEmpty()) {
            orders = "No Orders";
        } else {
            orders = singOrders.toString();
        }
        return "Singer [name: " + name + ", songName: " + songName +  ", numCurrentOrders: "+ singOrders.size() + ", currentOrders: {" + orders +"}, isAvailable: " + isAvailable + "]";
    }
}
