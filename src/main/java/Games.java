import java.util.ArrayList;

/**
 * Abstract class that represents a game. All subclasses are required to manage a list of players.
 * Implements the {@link Game} interface.
 */
public abstract class Games implements Game {

    /**
     * List of players participating in the game.
     */
    protected ArrayList<Player> players;

    /**
     * Constructs a new game with the specified list of players.
     *
     * @param players the list of players required to start the game.
     */
    public Games(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     * Starts the game. This method must be implemented by all subclasses per {@link Game} requirements
     */
    public abstract void play();
}
