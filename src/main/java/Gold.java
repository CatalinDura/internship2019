import java.time.LocalDate;

public class Gold extends Account {
    public final double interestBelow500=0.6;
    public final double interestAbove500=0.4;
    public int flagInterestBelow=0;
    public final String name="Gold";

    public Gold(double sold, LocalDate date){
        super(sold,date);
    }
    public double getInterest() {
        if (super.getSold()>500)
            if(super.getSold()<=5000) return 500*interestBelow500/100+(super.getSold()-500)*interestAbove500/100;
            else return 500*interestBelow500/100+4500*interestAbove500/100;
        else return super.getSold()*interestBelow500/100;
    }

    public double getInterestBelow500(){
        return interestBelow500;
    }

    public double getInterestAbove500(){
        return interestAbove500;
    }

    public String toString() {
        return "Gold{" +
                "interestBelow500=" + interestBelow500 +
                ", interestAbove500=" + interestAbove500 +
                ", flagInterestBelow=" + flagInterestBelow +
                ", name='" + name + '\'' +
                ", sold="+ super.getSold()+
                ", expirationDate=" + super.getExpirationDate().getDayOfMonth()+"."+super.getExpirationDate().getMonthValue()+"."+
                super.getExpirationDate().getYear()+
                '}';
    }
    public int getFlagBelow() {
        return flagInterestBelow;
    }

    public void setFlagBelow() {
        this.flagInterestBelow =1;
    }
}
