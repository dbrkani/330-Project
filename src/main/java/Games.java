import java.util.ArrayList;
//TODO: maybe make a little more robust
public abstract class Games implements Game {

    protected ArrayList<Player> players;

    public Games (ArrayList<Player> players){
        this.players = players;
    }

    public abstract void play();
}
