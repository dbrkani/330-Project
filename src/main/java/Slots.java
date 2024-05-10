import java.util.*;

public class Slots extends Games {

    private final List<List<String>> reels;
    private final Map<String, Integer> payouts;
    private Chips chips;

    public Slots(ArrayList<Player> players) {
        super(players);
        this.reels = generateReels();
        this.payouts = givePayOut();
        this.chips = new Chips();
    }

    private List<List<String>> generateReels() {
        String[] icons = { "🍒", "🍋", "🔔", "🐶", "7️⃣" };
        List<String> singleReel = new ArrayList<>(Arrays.asList(icons));
        List<List<String>> reels = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Collections.shuffle(singleReel, new Random());
            reels.add(new ArrayList<>(singleReel));
        }
        return reels;
    }

    private Map<String, Integer> givePayOut() {
        Map<String, Integer> payOut = new HashMap<>();
        payOut.put("🍒", 2);
        payOut.put("🍋", 3);
        payOut.put("🔔", 10);
        payOut.put("🐶", 50);
        payOut.put("7️⃣", 100);
        return payOut;
    }

    public void play() {
        // Show betting interface and allow each player to place their bets
        for (Player player : players) {
            chips.placeBet(player);
        }

        // Once all bets are placed, spin the reels once for all players
        List<String> outcome = spinReels();
        System.out.println("Spin results: " + outcome);


        for (Player player : players) {
            checkPayout(outcome, player);
        }
    }

    private void checkPayout(List<String> outcome, Player player) {
        if (outcome.get(0).equals(outcome.get(1)) && outcome.get(0).equals(outcome.get(2))) {
            System.out.println(player.getBet(0));
            int payoutMultiplier = payouts.get(outcome.get(1));
            int winnings = payoutMultiplier * player.getBet();
            player.addChips(winnings);
            System.out.println(player.getName() + " won " + winnings + " chips.");
        } else {
            System.out.println(player.getName() + " lost " + player.getBet() + " chips.");
        }
        player.resetBets();
    }

    private List<String> spinReels() {
        Random random = new Random();
        List<String> result = new ArrayList<>();
        for (List<String> reel : reels) {
            result.add(reel.get(random.nextInt(reel.size())));
        }
        return result;
    }
}
