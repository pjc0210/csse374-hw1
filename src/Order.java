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
    // PJ
    public String getSweetheartName() {
        return this.sweetheartName;
    }

    // PJ
    public void chargeCard(){
        System.out.println("Card charged for Order "+ orderID);
    }

    // Mason
    @Override
    public String toString() {
        return "Order [customerEmail=" + customerEmail + ", customerCardNum=" + customerCardNum + ", sweetheartName=" + sweetheartName + ", songOrdered=" + songOrdered + "]\n";
    }
}
