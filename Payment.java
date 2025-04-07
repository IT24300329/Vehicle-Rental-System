public class Payment
{
    private int ReferenceNo;
    private String customerName;
    private String vehicleName;
    private int days;
    private double pricePerDay;

    public Payment()
    {
        this.ReferenceNo = 0;
        this.customerName = "";
        this.vehicleName = "";
        this.days = 0;
        this.pricePerDay = 0;
    }

    public Payment(int ReferenceNo, String customerName,String vehicleName, int days, double pricePerDay)
    {
        this.ReferenceNo = ReferenceNo;
        this.customerName = customerName;
        this.vehicleName = vehicleName;

        if(days < 0)
        {
            System.out.println("Error: Days must be greater than 0");
        }
        else
        {
            this.days = days;
        }

        if(pricePerDay < 0)
        {
            System.out.println("Error: Price per day must be greater than 0");
        }
        else
        {
            this.pricePerDay = pricePerDay;
        }
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public void setDays(int days)
    {
        this.days = days;
    }

    public int getDays()
    {
        return days;
    }

    public void setPricePerDay(double pricePerDay)
    {
        this.pricePerDay = pricePerDay;
    }

    public double getPricePerDay()
    {
        return pricePerDay;
    }

    public double calculateTotal()
    {
        return days * pricePerDay;
    }

    public void generateReceipt()
    {
        System.out.println("Receipt");
        System.out.println("Reference No: " + ReferenceNo);
        System.out.println("Customer: " + customerName);
        System.out.println("Vehicle: " + vehicleName);
        System.out.println("Days: " + days);
        System.out.println("Total: " + calculateTotal());
    }
}
