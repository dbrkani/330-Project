import java.util.Scanner;

public class Player {
  private String name;
  private int chips;
  private int bet;
  private Scanner scanner;
  public Player(String name) {
    this.name = name;
    this.scanner = new Scanner(System.in);
    this.chips = 1000;
    this.bet = 0;
  }

  public void addChips(int amount) {
    chips += amount;
  }

  public void placeBet() {
    System.out.print("Player:" +getName()+"\nChips: "+getChips()+"\n\nEnter bet amount: ");
    int currentBet = scanner.nextInt();
    if (chips >= currentBet) {
      setChips(currentBet);
      bet = currentBet;
      System.out.println("Bet placed: " + bet);

    } else {
      System.out.println("Not enough money");
    }

  }

  public void resetBet(){
    bet = 0;
  }

  public int getBet() {
    return bet;
  }


  public int getChips() {
    return chips;
  }
  public void setChips(int betChip){
    chips -= betChip;
  }
  public String getName() {
    return name;
  }
}