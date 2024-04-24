import java.util.Scanner;

public class Casino {
    private Player player;
    private Game currentGame;
    private Scanner scanner;

    public Casino() {
      this.scanner = new Scanner(System.in);
      System.out.println("Enter your name:");
      String name = scanner.nextLine();
      this.player = new Player(name);

    }

    public void chooseGame() {
//Dashi: keep the program running while you still want to play
      boolean menu = true;
      while (menu = true){


        System.out.println("Player:" +player.getName()+"\nChips: "+player.getChips()+"\n\nChoose a game to play:");
        System.out.println("1: Slots");
        System.out.println("0: Exit");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                currentGame = new Slots(player);
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
          System.out.println("Player:" +player.getName()+"\nChips: "+player.getChips()+"\n\nEnter bet amount:");

            int bet = scanner.nextInt();

            game.placeBet(bet);
            game.play();
            System.out.println("Play again?");
            String response = scanner.next();
//Dashi: this is garbage. need to make a better way
            if (response.equals("no")) {
                keepPlaying = false;
                System.out.println("Exiting");
            }
        }
    }
}
