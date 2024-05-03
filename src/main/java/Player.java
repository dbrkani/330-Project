import java.util.Scanner;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
public class Player {
    private static final AtomicInteger count = new AtomicInteger(0);
    private final int  playerID;
    private final String name;
    private int chips;
    private final ArrayList<Integer> bets;
    private final Scanner scanner;
    public Player(String name) {
        this.name = name;
        this.scanner = new Scanner(System.in);
        this.chips = 1000;
        this.playerID = count.incrementAndGet();
        this.bets = new ArrayList<>();
    }



    public void addChips(int amount) {
        chips += amount;
    }

    public int getBet(){
        return bets.get(0);
    }

    public ArrayList<Integer> getAllBets(){
        return bets;
    }

    public int getBet(int index){
        return bets.get(index);
    }


    public boolean placeBet() {
        System.out.print("Player:" +getName()+"\nChips: "+getChips()+"\n\nEnter bet amount: ");
        int amount = scanner.nextInt();
        if (chips >= amount && amount>0) {
            chips -=amount;
            bets.add(amount);
            System.out.println("Bet placed: " + bets);
            System.out.println();
            return true;
        } else {
            System.out.println("Not enough money");
            System.out.println();
            return false;
        }

    }
    public boolean placeBet(int amount) {
        if (chips >= amount) {
            chips -=amount;
            System.out.println("Bet placed: " + amount);
            System.out.println();
            bets.add(amount);
            return true;

        } else {
            System.out.println("Not enough money");
            System.out.println();
            return false;
        }

    }

    public boolean placeBet(int index, int amount) {
        if (chips >= amount) {
            chips -=amount;
            bets.set(index, bets.get(index)+amount);
            System.out.println("Bet updated: " + bets.get(index));
            System.out.println();
            return true;

        } else {
            System.out.println("Not enough money");
            return false;
        }

    }
    public void resetBets(){
        bets.clear();
    }

    public int getChips() {
        return chips;
    }

    public String getName() {
        return name;
    }
    public int getID(){
        return playerID;
    }
}