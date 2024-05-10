import java.util.Scanner;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;


//Player holds Player information, including player IDs, names, and bet amounts.

public class Player {
/*
Player ID is an atomic integer that increments on every creation of a new player. Using ID allows
Players to have identical names
*/
    private static final AtomicInteger count = new AtomicInteger(0);
    private final int  playerID;
    private final String name;
    private long chips;
/*
Bets are tracked by the player class in order easily pull and create bets between games.
 */
    private final ArrayList<Integer> bets;
    private final Scanner scanner;
    public Player(String name) {
        this.name = name;
        this.scanner = new Scanner(System.in);
        this.chips = 1000;
        this.playerID = count.incrementAndGet();
        this.bets = new ArrayList<>();
    }


/*
place bet is a conditional that subtracts an amount from players available chips and adds it to the bet pool if available
returns true if successful.

Place bet is overloaded twice. Original takes user input for the bet, first overload allows for adding another bet
to the using the original bet amount, and second overload updates a bet based on ID
 */
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
    public boolean placeBet(long amount) {
        if (chips >= amount) {
            chips -=amount;
            System.out.println("Bet placed: " + amount);
            System.out.println();
            bets.add((int) amount);
            return true;

        } else {
            System.out.println("Not enough money");
            System.out.println();
            return false;
        }

    }

    public boolean placeBet(int index, long amount) {
        if (chips >= amount && amount>0) {
            chips -=amount;
            bets.set(index, (int) (bets.get(index)+amount));
            System.out.println("Bet updated: " + bets.get(index));
            System.out.println();
            return true;

        } else {
            System.out.println("Not enough money");
            return false;
        }

    }

    //getters and setters for bets for player variables.

    public void addChips(int amount) {
        chips += amount;
    }

    public int getBet(){
        return bets.get(0);
    }

    public ArrayList<Integer> getAllBets(){
        return bets;
    }

    public long getBet(int index){
        return bets.get(index);
    }
    public void resetBets(){
        bets.clear();
    }

    public long getChips() {
        return chips;
    }

    public String getName() {
        return name;
    }
    public int getID(){
        return playerID;
    }
}