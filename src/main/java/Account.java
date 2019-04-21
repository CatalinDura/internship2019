import java.time.LocalDate;

public abstract class Account {
    private double sold;
    private LocalDate expirationDate ;


    public Account(double sold,LocalDate date){
        this.sold=sold;
        this.expirationDate=date;
    }

    public double getSold() {
        return this.sold;
    }

    public double withdrawMoney(double money) {
        if(sold-money>=0){
            sold=sold-money;
            return money;
        }
        else return 0;
    }
    public void addSold(double sold) {
            this.sold=this.sold+sold;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public abstract int getFlagBelow();
    public abstract void setFlagBelow();
    public abstract double getInterest();
    public abstract double getInterestBelow500();
    public abstract double getInterestAbove500();
}
