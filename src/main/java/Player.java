import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
public class Player {
    private static final AtomicInteger count = new AtomicInteger(0);
    private int playerID;
    private String name;
    private int chips;
    private int bet;
    private Scanner scanner;
    public Player(String name) {
        this.name = name;
        this.scanner = new Scanner(System.in);
        this.chips = 1000;
        this.playerID = count.incrementAndGet();
    }



    public void addChips(int amount) {
        chips += amount;
    }

    public int placeBet() {
        System.out.print("Player:" +getName()+"\nChips: "+getChips()+"\n\nEnter bet amount: ");
        int bet = scanner.nextInt();
        if (chips >= bet) {
            chips -=bet;
            System.out.println("Bet placed: " + bet);
            return bet;
        } else {
            System.out.println("Not enough money");
            return 0;
        }

    }
    public int placeBet(int bet) {
        if (chips >= bet) {
            chips -=bet;
            System.out.println("Bet placed: " + bet);
            return bet;

        } else {
            System.out.println("Not enough money");
            return 0;
        }

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