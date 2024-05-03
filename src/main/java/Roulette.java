import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.HashMap;

public class Roulette extends Games {
    private final Random random;
    private final ArrayList<RouletteNum> numbers;
    private HashMap<Integer,ArrayList<ArrayList<RouletteNum>>> userNumbers;
    private HashMap<Integer,ArrayList<Integer>> userChoices;
    private Scanner scanner;

    public Roulette(ArrayList<Player> players) {
        super(players);
        this.random = new Random();
        this.numbers = new ArrayList<>();
        for(int i =0;i<38;i++){
            numbers.add(new RouletteNum(i));
        }
        userNumbers = new HashMap<>();
        userChoices = new HashMap<>();
        this.scanner = new Scanner(System.in);
    }

    // Simulates spinning the roulette wheel
    public int spin() {
        return random.nextInt(38); // Generates a number between 0 and 36
    }

    public void calcWinner(int ball){
        System.out.println("Spinning....");
        System.out.println("Landed on "+ball+"!");
        RouletteNum winner = numbers.get(ball);
        for(Player player : players) {
            ArrayList<ArrayList<RouletteNum>> userNums = userNumbers.get(player.getID());
            ArrayList<Integer> userChoice = userChoices.get(player.getID());
            for (int i = 0; i < userNums.size(); i ++) {
                int thisBet = player.getBet(i);
                int  multiplier = RouletteBets.calcWinner(winner,userNums.get(i),userChoice.get(i));
                if(multiplier >0){
                    System.out.println("Won +"+(thisBet*multiplier-thisBet)+" chips!");
                    player.addChips(thisBet*multiplier);
                }

            }

        }
    }

    public void play() {
        RouletteTable.printRouletteTable();
        for (Player player : players) {
            System.out.println(player.getName()+": "+player.getChips()+" chips");
            ArrayList<ArrayList<RouletteNum>> thisUsersNumbers= new ArrayList<>();
            ArrayList<Integer> thisUserChoices = new ArrayList<>();
            String userIn;
            do {
                ArrayList<RouletteNum> thisUsersNumber = new ArrayList<>();
                System.out.println("Place Bet:");
                int betHolder = 0;
                userIn = scanner.nextLine().trim();  // Use nextLine() to handle full user input immediately
                if (userIn.equals("exit") || userIn.isEmpty()) {
                    break;
                }

                try {
                    int betAmount = Integer.parseInt(userIn);  // Assuming bet amount or identifier is entered
                    betHolder = betAmount;
                    boolean bet = player.placeBet(betAmount);

                    if (bet) {
                        System.out.println("(1)Single (2)Double (3)Triple (4)Quadruple");
                        System.out.println("(5)Sixes (6)Dozen (7)Column (8)First Five");
                        System.out.println("(9)First Half (10)Second Half (11)Red (12)Black");
                        System.out.println("(13)Odd (14)Even (0)Explain the bets again");

                        userIn = scanner.nextLine().trim();
                        int userInt = Integer.parseInt(userIn);
                        if (userInt == 0) {
                            RouletteTable.printBets();
                        }
                        else if (userInt < 8 && userInt > 0) {
                            System.out.println("Pick your numbers");
                            userIn = scanner.nextLine();
                            while (scanner.hasNext())
                                thisUsersNumber.add(numbers.get(Integer.parseInt(userIn)));
                        }
                        else{
                            thisUsersNumber.add(numbers.get(RouletteBets.handleOpenBets(userInt)));
                        }
                        if (RouletteBets.validBets(userInt, thisUsersNumber)) {
                            thisUserChoices.add(userInt);
                            thisUsersNumbers.add(thisUsersNumber);
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input");
                    player.addChips(betHolder);
                }
            } while (!userIn.equals("exit") && !userIn.isEmpty());
            userChoices.put(player.getID(), thisUserChoices);
            userNumbers.put(player.getID(), thisUsersNumbers);
        }
        calcWinner(random.nextInt(38));
    }

}
