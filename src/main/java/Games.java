public abstract class Games implements Game {

  protected Player activePlayer;

    public Games (Player playerOne){
      this.activePlayer = playerOne;
    }

  public abstract void play();
}
