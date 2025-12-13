public class Order {
    private final int orderID;
    private String customerEmail;
    private long customerCardNum;
    private String sweetheartName;
    private String songOrdered;

    public Order(int orderID, String customerEmail, long customerCardNum, String sweetheartName, String songOrdered) {
        this.orderID = orderID;
        this.customerEmail = customerEmail;
        this.customerCardNum = customerCardNum;
        this.sweetheartName = sweetheartName;
        this.songOrdered = songOrdered;
    }

    public String getSweetheartName() {
        return this.sweetheartName;
    }

    public void chargeCard(){
        System.out.println("Card charged for Order "+ orderID);
    }

    @Override
    public String toString() {
        return "Order [customerEmail=" + customerEmail + ", customerCardNum=" + customerCardNum + ", sweetheartName=" + sweetheartName + ", songOrdered=" + songOrdered + "]\n";
    }
}
