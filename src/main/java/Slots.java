import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Collections;

public class Slots {
  private List<List<String>> reels;
  private Map<String, Integer> payouts;
  private int currentBet;
  public boolean test;

  public Slots() {
    this.reels = generateReels();
    this.payouts = givePayOut();
    this.currentBet = 0;

  }

  private List<List<String>> generateReels() {
    String[] icons = { "cherry", "lemon", "bell", "bar", "seven" };
    List<String> singleReel = Arrays.asList(icons);
    List<List<String>> reels = new ArrayList<List<String>>();

    for (int i = 0; i < 3; i++) {
      Collections.shuffle(singleReel);
      reels.add(singleReel);
    }
    return reels;
  }

  private Map<String, Integer> givePayOut() {
    Map<String, Integer> PayOut = new HashMap<String, Integer>();
    PayOut.put("cherry", 100);
    PayOut.put("lemon", 200);
    PayOut.put("bell", 500);
    PayOut.put("bar", 750);
    PayOut.put("seven", 1000);
    return PayOut;
  }

  public void placeBet(int bet) {
    this.currentBet = bet;
  }

  public List<String> spinReels() {
    Random random = new Random();
    List<String> result = new ArrayList<String>();
    for (List<String> reel : reels) {
      String res = reel.get(random.nextInt(reel.size()));
      System.out.print(res + " ");
      result.add(res);
    }
    System.out.println();
    return result;

  }

  public int play() {
    List<String> outcome = spinReels();
    Integer payoutMulti;
    if (outcome.get(0).equals(outcome.get(1)) && outcome.get(0).equals(outcome.get(2))) {
      payoutMulti = payouts.get(outcome.get(1));
      System.out.println("You won " + payoutMulti * currentBet + " Chips");


    } else {
      payoutMulti = 0;
      System.out.println("L");
    }
    return payoutMulti * currentBet;
  }

  public void resetBet() {
    this.currentBet = 0;
  }
}
