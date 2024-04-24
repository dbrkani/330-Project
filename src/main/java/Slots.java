import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Collections;

public class Slots extends Games implements Game {

  // Dashi: Reels hold the columns of icons, payouts give the multiplier. Lists of
  // lists must be explicitly declared.
  private List<List<String>> reels;
  private Map<String, Integer> payouts;

  // Dashi: constructor generates a random set of reels each time
  public Slots(Player playerOne) {
    super(playerOne);
    this.reels = generateReels();
    this.payouts = givePayOut();
  }

  private List<List<String>> generateReels() {
    // Dashi: basic five icons in every reel
    String[] icons = { "🍒", "🍋", "🔔", "🐶", "7️⃣" };
    // Dashi: seed reel for randomization
    List<String> singleReel = Arrays.asList(icons);
    // Dashi: holds randomized reels
    List<List<String>> reels = new ArrayList<List<String>>();

    // Dashi: create randomized reels, then add them to the reel container.
    for (int i = 0; i < 3; i++) {
      Collections.shuffle(singleReel);
      reels.add(singleReel);
    }
    return reels;
  }

  // Dashi: map used to streamline payouts
  private Map<String, Integer> givePayOut() {
    Map<String, Integer> PayOut = new HashMap<String, Integer>();
    PayOut.put("🍒", 100);
    PayOut.put("🍋", 200);
    PayOut.put("🔔", 500);
    PayOut.put("🐶", 750);
    PayOut.put("7️⃣", 1000);
    return PayOut;
  }


  // Dashi: logic for a spin. returns 3 random icons.
  public List<String> spinReels() {
    Random random = new Random();
    List<String> result = new ArrayList<String>();
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
    activePlayer.placeBet();
    int currentBet = activePlayer.getBet();
    if (currentBet != 0) {

      List<String> outcome = spinReels();
      Integer payoutMulti = 0;

      // Dashi: check if theres a match. Looks terrible and can probably be refactore
      if (outcome.get(0).equals(outcome.get(1)) && outcome.get(0).equals(outcome.get(2))) {
        payoutMulti = payouts.get(outcome.get(1));
        System.out.println(activePlayer.getName() + " won " + payoutMulti * currentBet + " chips");
      } else {
        System.out.println("L, you lost " + currentBet + " chips");
      }
      activePlayer.addChips(payoutMulti * currentBet);
    }
    activePlayer.resetBet();
  }

}
