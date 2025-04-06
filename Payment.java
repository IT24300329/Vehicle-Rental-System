public class Payment
{
    private String customerName;
    private String vehicleName;
    private int days;
    private double pricePerDay;

    public Payment(String customerName,String vehicleName, int days, double pricePerDay)
    {
        this.customerName = customerName;
        this.vehicleName = vehicleName;
        this.days = days;
        this.pricePerDay = pricePerDay;
    }

    public double calculateTotal()
    {
        return days * pricePerDay;
    }

    public void generateReceipt()
    {
        System.out.println("Receipt");
        System.out.println("Customer: " + customerName);
        System.out.println("Vehicle: " + vehicleName);
        System.out.println("Days: " + days);
        System.out.println("Total: " + calculateTotal());
    }
}
