import java.util.ArrayList;

public abstract class Games implements Game {

    protected ArrayList<Player> players;

    public Games (ArrayList<Player> players){
        this.players = players;
    }

    public abstract void play();
}
