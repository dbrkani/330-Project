import java.util.Scanner;
import java.util.ArrayList;

public class Casino {
    private ArrayList<Player> players;
    private Game currentGame;
    private Scanner scanner;
//casino initializes starting players, and handles which game to play.
    public Casino() {
        this.scanner = new Scanner(System.in);
        this.players = new ArrayList<Player>();
        System.out.println("Enter player names:");
//TODO: change to do-while
        //gets each users name, adds them to player list
        String userIn = scanner.nextLine();
        while(!userIn.equals("exit")&&!userIn.equals("")) {

            Player player = new Player(userIn);
            players.add(player);
            userIn=scanner.nextLine();
        }


    }


    public void chooseGame() {
        // Keep the program running while you still want to play
        boolean menu = true;
        while (menu) {
            for(Player player :players){
                System.out.println("Player "+player.getID()+":" + player.getName() + "\nChips: " + player.getChips());
            }
            System.out.println("\n\nChoose a game to play:\n1: Slots\n2: Blackjack\n3: Roulette\n0: Exit");

            int choice = scanner.nextInt();
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
                    System.out.println("Exiting");
                    menu = false;
                    return;
            }
            playGame(currentGame);
        }
    }

    private void playGame(Game game) {

        boolean keepPlaying = true;
        while (keepPlaying) {
            game.play();
            System.out.println("Continue?");
            String response = scanner.next();
            // TODO: this is garbage. need to make a better way
            if (response.equals("no")) {
                keepPlaying = false;
                System.out.println("Exiting");
            }
        }
    }
}
