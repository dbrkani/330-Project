import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Collections;



public class Slots extends Games implements Game {

    // Reels hold the columns of icons, payouts give the multiplier.
    private final List<List<String>> reels;
    private final Map<String, Integer> payouts;

    /**
     * constructor generates a random set of reels each time
     * @param players generates player list
     */

    public Slots(ArrayList<Player> players) {
        super(players);
        this.reels = generateReels();
        this.payouts = givePayOut();
    }
//generate the arrays that will hold the icons for each column in the slot machine
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

    // map used to streamline payouts
    private Map<String, Integer> givePayOut() {
        Map<String, Integer> PayOut = new HashMap<>();
        PayOut.put("🍒", 2);
        PayOut.put("🍋", 3);
        PayOut.put("🔔", 10);
        PayOut.put("🐶", 50);
        PayOut.put("7️⃣", 100);
        return PayOut;
    }


    // logic for a spin. returns 3 random icons.

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

    @Override

    public void play() {
    //iterate through available players
        for(Player player:players){
            boolean currentBet = player.placeBet();
            //check if bet was placed. if so spin the wheel and check if user has a match.
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
