import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class User {

    private List<Account> lista=new ArrayList<Account>();
    private LocalDateTime currentDataTime;

    public User(LocalDateTime currentDataTime){
        this.currentDataTime=currentDataTime;
        System.out.println(currentDataTime.toString());
    }

    public void addAccount(Account x){
        lista.add(x);
    }

    public List<Account> redistributeMoney(List<Account> accounts){

        //Verificam care dintre conturi vor fi valide peste un an pentru a putea beneficia de interest rate

        List<Account> validAccountOverAYear=accounts.stream()
                .filter(x->x.getExpirationDate().isAfter(LocalDate.of(currentDataTime.getYear()+1,
                        currentDataTime.getMonthValue(), currentDataTime.getDayOfMonth())))
                .collect(Collectors.toList());

        //Salvam de asemenea si conturile care vor fi invalide peste un an

        List<Account> invalidAccountOverAYear=accounts.stream()
                .filter(x->x.getExpirationDate().isBefore(LocalDate.of(currentDataTime.getYear()+1,
                        currentDataTime.getMonthValue(), currentDataTime.getDayOfMonth())))
                .collect(Collectors.toList());

        //Sortam conturile valide in functie de interest rate pentru valori mai mici de 500 lei
        List<Account> listInterestBelow = validAccountOverAYear.stream()
                .sorted(Comparator.comparingDouble(x->x.getInterestBelow500()))
                .collect(Collectors.toList());

        //Sortam conturile valide in functie de interest rate pentru valori mai mari de 500 lei

        List<Account> listInterestAbove = validAccountOverAYear.stream()
                .sorted(Comparator.comparingDouble(x->x.getInterestAbove500()))
                .collect(Collectors.toList());

        double soldTotal=0;
        for(Account x:lista){
            soldTotal=soldTotal+x.withdrawMoney(x.getSold());
        }

        List<Account> result=new ArrayList<Account>();
        List<Account> sortedInterestList=intercalateInterestLists(listInterestBelow,listInterestAbove);


        //Adaugam bani in fiecare cont in functie de valoarea existenta deja in cont si
        // de valoare disponibila in celelalte conturi
        for(Account aux:sortedInterestList){
            if(aux.getFlagBelow()==0){
                aux.setFlagBelow();
                if(soldTotal>500){
                    aux.addSold(500);
                    soldTotal=soldTotal-500;
                }
                else{
                    aux.addSold(soldTotal);
                    soldTotal=0;
                }
            }
            else {
                if (soldTotal > 4500) {
                    aux.addSold(4500);
                    soldTotal = soldTotal - 4500;
                } else {
                    aux.addSold(soldTotal);
                    soldTotal = 0;
                }
                result.add(aux);
            }
        }

        //reinitializam lista de conturi cu noile valori
        if(soldTotal>0){
            result.get(0).addSold(soldTotal);
        }
        lista=result;

        //Adaugam conturile care peste un an vor fi invalide
        for(Account aux:invalidAccountOverAYear){
            lista.add(aux);
        }
        return result;
    }

    public List<Account> intercalateInterestLists(List<Account> listInterestBelow,List<Account> listInterestAbove){

        //Interclasam cele 2 liste astfel incat valorile interest rate mai mari sa fie primele
        //Vom avea 2 aparitii ale aceluiasi obiect in lista, una pentru fiecare interest rate a contului respectiv

        List<Account> rez=new ArrayList<Account>();
        int index1=listInterestAbove.size()-1;
        int index2=index1;
        while(index1>=0 && index2>=0)
            if(listInterestAbove.get(index1).getInterestAbove500()>=listInterestBelow.get(index2).getInterestBelow500()){
                rez.add(listInterestAbove.get(index1));
                index1=index1-1;
            }
            else {
                rez.add(listInterestBelow.get(index2));
                index2 = index2 - 1;
            }

        if(index1>=0)
            while(index1>=0){
                rez.add(listInterestAbove.get(index1));
                index1=index1-1;
            }
        else while(index2>=0){
            rez.add(listInterestBelow.get(index2));
            index2=index2-1;
        }
        return rez;
    }

    public void calculateInteresetOverAPeriod(int months){
        int times=months/12;
        for(int i=0;i<times;i++){
            for(Account aux:lista){
                aux.addSold(aux.getInterest());
            }
        }
    }

    public List<Account> getLista() {
        return lista;
    }

    public static void main(String[] args){
        LocalDateTime localDateTime=LocalDateTime.of(2019,3,19,11,30);
        User x=new User(localDateTime);

        LocalDate date1=LocalDate.of(2020,5,23);
        Account c1=new Silver(5000,date1);

        LocalDate date2=LocalDate.of(2020,7,5);
        Account c2=new Gold(700,date2);

        LocalDate date3=LocalDate.of(2020,3,15);
        Account c3=new Platinum(300,date3);

        x.addAccount(c1);
        x.addAccount(c2);
        x.addAccount(c3);

        x.redistributeMoney(x.getLista());
        x.calculateInteresetOverAPeriod(39);
        x.getLista().forEach(System.out::println);

    }

}
