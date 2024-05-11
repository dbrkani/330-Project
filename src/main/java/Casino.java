import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * The Casino class manages the casino game operations including player management and game selection.
 */
public class Casino {
    private final ArrayList<Player> players;
    private final Scanner scanner;
    private boolean menu;

    /**
     * Constructs a new Casino instance. Initializes the player list and scanner,
     * and prompts the user to enter player names. Entering "exit" concludes the player input phase.
     */
    public Casino() {
        this.scanner = new Scanner(System.in);
        this.players = new ArrayList<>();
        this.menu = true;
        System.out.println("Enter player names, then press 'enter'. Type 'exit' to start playing:");

        String userIn;
        do {
            userIn = scanner.nextLine();
            if (!userIn.equalsIgnoreCase("exit") && !userIn.isEmpty()) {
                Player player = new Player(userIn);
                players.add(player);
            }
        } while (!userIn.equalsIgnoreCase("exit") && !userIn.isEmpty());
    }

    /**
     * Checks for players with no remaining chips and removes them from the game.
     * Announces players who are out and determines if the game should continue.
     *
     * @return true if there are still players in the game, false if all players are out.
     */
    public boolean checkForBums() {
        List<Player> bums = players.stream().filter(player -> player.getChips() == 0).toList();
        if (!bums.isEmpty()) {
            System.out.print("Player" + (bums.size() > 1 ? "s " : " "));
            bums.forEach(bum -> System.out.print(bum.getName() + (bum.equals(bums.get(bums.size() - 1)) ? "" : ", ")));
            System.out.println((bums.size() == 1 ? " is " : " are ") + "out!");
        }
        players.removeAll(bums);
        if (players.isEmpty()) {
            menu = false;
            return false;
        }
        return true;
    }

    /**
     * Initiates the game selection process. Allows players to choose from available games.
     * Continues to offer game choices until the exit condition is met.
     */
    public void chooseGame() {
        while (menu) {
            System.out.println("Players and Chips:");
            for (Player player : players) {
                System.out.println("Player " + player.getID() + ": " + player.getName() + " | Chips: " + player.getChips());
            }
            System.out.println("\nChoose a game to play (or type '0' to exit):");
            System.out.println("1: Slots\n2: Blackjack\n3: Roulette\n0: Exit");

            int choice = scanner.nextInt();
            if (choice == 0) {
                System.out.println("Exiting Casino...");
                scanner.close();
                return;
            }

            Game currentGame;
            switch (choice) {
                case 1:
                    currentGame = new Slots(players);
                    break;
                case 2:
                    currentGame = new Blackjack(players);
                    break;
                case 3:
                    currentGame = new Roulette(players);
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid game.");
                    continue;
            }
            playGame(currentGame);
        }
        System.out.println("Game over!");
    }

    /**
     * Manages the game playing process. Continues to play the selected game as long as there are valid players
     * and until the player decides to stop or switch games.
     *
     * @param game The game to be played.
     */
    private void playGame(Game game) {
        while (checkForBums()) {
            game.play();
            if (checkForBums()) {
                System.out.println("Play another round? (yes/no)");
                String response = scanner.next();
                if (response.equalsIgnoreCase("no")) {
                    System.out.println("Returning to game selection...");
                    break;
                }
            }
        }
    }
}