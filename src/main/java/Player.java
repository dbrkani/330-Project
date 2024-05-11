import java.util.Scanner;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a player in a game. Manages player information - IDs, names, and betting amounts.
 * Player IDs are unique and automatically increment when a player is created, allowing players to have identical names.
 */
public class Player {
    /**
     * Static counter for generating unique player IDs.
     */
    protected static final AtomicInteger count = new AtomicInteger(0);

    /**
     * Unique identifier for the player.
     */
    protected final int playerID;

    /**
     * Name of the player.
     */
    protected  String name;

    /**
     * Amount of chips the player has for betting.
     */
    protected long chips;

    /**
     * List of bet amounts placed by the player.
     */
    private final ArrayList<Integer> bets;

    /**
     * Scanner to read player input.
     */
    private final Scanner scanner;

    /**
     * Constructs a new player with a specified name. Initial chips are set to 1000.
     *
     * @param name the name of the player
     */
    public Player(String name) {
        this.name = name;
        this.scanner = new Scanner(System.in);
        this.chips = 1000;
        this.playerID = count.incrementAndGet();
        this.bets = new ArrayList<>();
    }

    /**
     * Places a bet by taking an amount input from the player.
     * Amount is subtracted from the player's chips and added to their bet pool if they have enough chips.
     *
     * @return true if the bet is successfully placed, false otherwise.
     */
    public boolean placeBet() {
        System.out.print("Player: " + getName() + "\nChips: " + getChips() + "\n\nEnter bet amount: ");
        int amount = scanner.nextInt();
        if (chips >= amount && amount > 0) {
            chips -= amount;
            bets.add(amount);
            System.out.println("Bet placed: " + amount);
            System.out.println();
            return true;
        } else {
            System.out.println("Not enough money");
            System.out.println();
            return false;
        }
    }

    /**
     * Places specified bet amount directly, updating the player's chips and bet pool.
     *
     * @param amount the amount of the bet
     * @return true if the bet is successfully placed, false otherwise.
     */
    public boolean placeBet(long amount) {
        if (chips >= amount) {
            chips -= amount;
            bets.add((int) amount);
            System.out.println("Bet placed: " + amount);
            System.out.println();
            return true;
        } else {
            System.out.println("Not enough money");
            System.out.println();
            return false;
        }
    }

    /**
     * Updates an existing bet by index with an additional amount.
     *
     * @param index  the index of the bet to update
     * @param amount the additional amount to add to the bet
     * @return true if the bet is successfully updated, false otherwise.
     */
    public boolean placeBet(int index, long amount) {
        if (chips >= amount && amount > 0 && index >= 0 && index < bets.size()) {
            chips -= amount;
            bets.set(index, (int) (bets.get(index) + amount));
            System.out.println("Bet updated: " + bets.get(index));
            System.out.println();
            return true;
        } else {
            System.out.println("Not enough money");
            return false;
        }
    }

    // Additional methods for managing chips and bets are documented below.

    /**
     * Adds a specified amount of chips to the player's total.
     *
     * @param amount the amount of chips to add
     */
    public void addChips(int amount) {
        chips += amount;
    }

    /**
     * Retrieves the first bet in the list.
     *
     * @return the first bet amount
     */
    public int getBet() {
        return bets.getFirst();
    }

    public ArrayList<Integer> getAllBets() {
        return bets;
    }

    /**
     * Retrieves a bet by index.
     *
     * @param index the index of the bet to retrieve
     * @return the bet amount at the index
     */
    public long getBet(int index) {
        return bets.get(index);
    }

    /**
     * Clears all bets from the player's bet list.
     */
    public void resetBets() {
        bets.clear();
    }

    /**
     * Returns the total number of chips the player has.
     *
     * @return the total chips
     */
    public long getChips() {
        return chips;
    }

    /**
     * Returns the name of the player.
     *
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the unique ID of the player.
     *
     * @return the player ID
     */
    public int getID() {
        return playerID;
    }
    /**
     * Changes the name of the player.
     *
     * @param newName name to change to
     */

    public void setName(String newName){
        name = newName;
    }
}
