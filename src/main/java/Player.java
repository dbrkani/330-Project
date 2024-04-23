public class Player {
    private String name;
    private int chips;
    private int bet;

    public Player(String name) {
        this.name = name;
        this.chips = 1000;
        this.bet = 0;
    }

    public void addChips(int amount) {
        chips += amount;
    }

    public void bet(int amount) {
      if (chips > amount){
      chips -= amount;
        bet = amount;
        }
      else
        amount = 0;
    }

    public int getBet(){
      return bet;
    }

    public int getChips() {
        return chips;
    }

    public String getName() {
        return name;
    }
}