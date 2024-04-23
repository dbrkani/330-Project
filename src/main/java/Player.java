public class Player {
    private String name;
    private int chips;

    public Player(String name) {
        this.name = name;
        this.chips = 1000;
    }

    public void addChips(int amount) {
        chips += amount;
    }

    public int bet(int amount) {
      if (chips > amount)
      chips -= amount;
      else
        amount = 0;
      return amount;
    }

    public int getChips() {
        return chips;
    }

    public String getName() {
        return name;
    }
}