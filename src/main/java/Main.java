public class Main {
  public static void main(String[] args) {
    Player Dashi = new Player("Dashi");
    Slots test = new Slots(Dashi);
    Dashi.bet(50);
    System.out.println(Dashi.getName()+" - Chips: "+Dashi.getChips());
    test.play(Dashi,Dashi.getBet());
    System.out.println(Dashi.getName()+" - Chips: "+Dashi.getChips());
  }

}