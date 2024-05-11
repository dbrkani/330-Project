import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Collections;


/**
 * {@code Slots} extends {@code Games}, simulating a slot machine game
 * It manages the spinning reels and calculates payouts based on the game outcomes
 */

public class Slots extends Games {
    /**
     * Stores the icon reels of the slot machine
     */
    private final List<List<String>> reels;
    /**
     * Maps icons to their respective payout multipliers
     */

    private final Map<String, Integer> payouts;

    /**
     * Constructs a new Slots game with the specified list of players
     * Initializes the reels and payouts for the slot machine
     *
     * @param players the list of players participating in the slot game
     */

    public Slots(ArrayList<Player> players) {
        super(players);
        this.reels = generateReels();
        this.payouts = givePayOut();
    }
    /**
     * Generates a set of reels with randomized icons for the slot machine
     * Each reel contains a standard set of icons, which are shuffled to produce the final reel
     *
     * @return a list of lists containing the shuffled icons for each reel
     */
    private List<List<String>> generateReels() {
        // basic five icons in every reel
        String[] icons = { "🍒", "🍋", "🔔", "🐶", "7️⃣" };
        // seed reel for randomization
        List<String> singleReel = Arrays.asList(icons);
        // holds randomized reels
        List<List<String>> reels = new ArrayList<>();
        // create randomized reels, then add them to the reel container.
        for (int i = 0; i < 3; i++) {
            Collections.shuffle(singleReel);
            reels.add(singleReel);
        }
        return reels;
    }


    /**
     * Defines the payouts for each icon. Each icon is mapped to an integer that acts as its payout multiplier
     *
     * @return a map linking each icon to its payout multiplier
     */
    private Map<String, Integer> givePayOut() {
        Map<String, Integer> PayOut = new HashMap<>();
        PayOut.put("🍒", 2);
        PayOut.put("🍋", 3);
        PayOut.put("🔔", 10);
        PayOut.put("🐶", 50);
        PayOut.put("7️⃣", 100);
        return PayOut;
    }

    /**
     * Simulates a single spin of the slot machine. Randomly selects one icon from each reel and displays the results
     *
     * @return a list of strings representing the icons selected from each reel
     */
    public List<String> spinReels() {
        Random random = new Random();
        List<String> result = new ArrayList<>();
        for (List<String> reel : reels) {
            String res = reel.get(random.nextInt(reel.size()));
            System.out.print(res + "  ");
            result.add(res);
        }
        System.out.println();
        return result;

    }


    /**
     * Simulates playing a round of the slot game for each player. Each player places a bet, spins the reels,
     * and receives payouts based on the outcome of the spin.
     */
    @Override
    public void play() {
    //iterate through available players
        for(Player player:players){
            boolean currentBet = player.placeBet();
            //check if bet was placed. if so spin the wheel and check if user has a match
            // might be able to update for betting on multiple lines.
            if (currentBet) {
                List<String> outcome = spinReels();
                Integer payoutMulti = 0;
                // Check if there's a match, and payout
                boolean match = outcome.get(0).equals(outcome.get(1)) && outcome.get(0).equals(outcome.get(2)); //legible
                if (match) {
                    payoutMulti = payouts.get(outcome.get(1));
                    System.out.println(player.getName() + " won " + payoutMulti * player.getBet() + " chips");
                } else {
                    System.out.println("You lost " + player.getBet() + " chips");
                }
                player.addChips(payoutMulti * player.getBet());
            }
            System.out.println();
        player.resetBets();
        }
    }

}
