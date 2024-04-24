import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Collections;

public class Slots implements Game {

//Dashi: Reels hold the columns of icons, payouts give the multiplier. Lists of lists must be explicitly declared.
  private List<List<String>> reels;
  private Map<String, Integer> payouts;
  private int currentBet;
  private Player activePlayer;

//Dashi: constructor generates a random set of reels each time
  public Slots(Player playerOne) {
    this.reels = generateReels();
    this.payouts = givePayOut();
    this.currentBet = 0;
    this.activePlayer = playerOne;
  }

  private List<List<String>> generateReels() {
//Dashi: basic five icons in every reel
    String[] icons = { "🍒", "🍋", "🔔", "🐶", "7️⃣" };
//Dashi: seed reel for randomization
    List<String> singleReel = Arrays.asList(icons);
//Dashi: holds randomized reels
    List<List<String>> reels = new ArrayList<List<String>>();

//Dashi: create randomized reels, then add them to the reel container.
    for (int i = 0; i < 3; i++) {
      Collections.shuffle(singleReel);
      reels.add(singleReel);
    }
    return reels;
  }

//Dashi: map used to streamline payouts
  private Map<String, Integer> givePayOut() {
    Map<String, Integer> PayOut = new HashMap<String, Integer>();
    PayOut.put("🍒", 100);
    PayOut.put("🍋", 200);
    PayOut.put("🔔", 500);
    PayOut.put("🐶", 750);
    PayOut.put("7️⃣", 1000);
    return PayOut;
  }


//Dashi: TODO: implement throws and catches
  @Override
  public void placeBet(int bet) {
//Dashi: Ensure that the player isnt betting negatively
    if (bet > 0 && bet <= activePlayer.getChips()) {
      activePlayer.bet(bet);
      this.currentBet = bet;
      System.out.println("Bet placed: " + bet);
    } else {
      System.out.println("ya need 💲💲💲 to play");
    }
  }

//Dashi: reset bet after a spin
  public void resetBet() {
    this.currentBet = 0;
  }

//Dashi: logic for a spin. returns 3 random icons.
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

    //Dashi: logic to determine a win. could be its own method. would need to tweak
    // if we wanted to include diagonal matches
    List<String> outcome = spinReels();
    Integer payoutMulti = 0;
    //Dashi: check if theres a match. Looks terrible and can probably be refactored

    if (outcome.get(0).equals(outcome.get(1)) && outcome.get(0).equals(outcome.get(2))) {
      payoutMulti = payouts.get(outcome.get(1));
      System.out.println(activePlayer.getName() + " won " + payoutMulti * currentBet + " chips");
    } else {
      System.out.println("L, you lost " + currentBet + " chips");
    }

    activePlayer.addChips(payoutMulti * currentBet);
    resetBet();
  }

}
