import java.time.LocalDate;
import java.time.LocalDateTime;

public class Account {
    private double sold;
    private LocalDate expirationDate ;
    public double interestBelow500;
    public double interestAbove500;
    public int flagInterestBelow=0;
    public String name;

    public Account(double sold,LocalDate date,double interestBelow500,double interestAbove500,String name){
        this.sold=sold;
        this.expirationDate=date;
        this.name=name;
        this.interestBelow500=interestBelow500;
        this.interestAbove500=interestAbove500;
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

    public int getFlagBelow() {
        return flagInterestBelow;
    }

    public void setFlagBelow() {
        this.flagInterestBelow =1;
    }
    public double getInterest() {
        if (sold>500)
            if(sold<=5000) return 500*interestBelow500/100+(sold-500)*interestAbove500/100;
            else return 500*interestBelow500/100+4500*interestAbove500/100;
        else return sold*interestBelow500/100;
    }
    public double getInterestBelow500(){
        return interestBelow500;
    }

    public double getInterestAbove500(){
        return interestAbove500;
    }

    @Override
    public String toString() {
        return "Account{" +
                "sold=" + sold +
                ", expirationDate=" + expirationDate +
                ", interestBelow500=" + interestBelow500 +
                ", interestAbove500=" + interestAbove500 +
                ", flagInterestBelow=" + flagInterestBelow +
                ", name='" + name + '\'' +
                '}';
    }
}
