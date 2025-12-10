public class Order {
    private int orderID;
    private String customerEmail;
    private int customerCardNum;
    private String sweetheartName;
    private String songOrdered;

    public Order(int orderID, String customerEmail, int customerCardNum, String sweetheartName, String songOrdered) {
        this.orderID = orderID;
        this.customerEmail = customerEmail;
        this.customerCardNum = customerCardNum;
        this.sweetheartName = sweetheartName;
        this.songOrdered = songOrdered;
    }

    public String getSweetheartName() {
        return this.sweetheartName;
    }

    public boolean chargeCard(){
        System.out.println("Card charged for Order "+ orderID);
        return true;
    }
}
