import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.HashMap;

public class Roulette extends Games {
    //random number generator
    private final Random random;
    //hold "wheel"
    private final ArrayList<RouletteNum> numbers;
    //hold users chosen numbers for bet
    private final HashMap<Integer,ArrayList<ArrayList<RouletteNum>>> userNumbers;
    //hold users bet choice.
    private final HashMap<Integer,ArrayList<Integer>> userChoices;
    private final Scanner scanner;

    public Roulette(ArrayList<Player> players) {
        super(players);
        this.random = new Random();
        this.numbers = new ArrayList<>();
        //generate wheel.
        for(int i =0;i<38;i++){
            numbers.add(new RouletteNum(i));
        }
        userNumbers = new HashMap<>();
        userChoices = new HashMap<>();
        this.scanner = new Scanner(System.in);
    }

    //generate a number between 0 and 37
    public int spin() {
        return random.nextInt(38);
    }

    //calculate winner, takes random number as parameter
    public void calcWinner(int ball){
        System.out.println("Spinning....");
        System.out.println("Landed on "+numbers.get(ball).getNumString()+"!");
        //get the number class for the spin result
        RouletteNum winner = numbers.get(ball);

        //determine if player won or not
        for(Player player : players) {
            //New array lists for particular user choice
            ArrayList<ArrayList<RouletteNum>> userNums = userNumbers.get(player.getID());
            ArrayList<Integer> userChoice = userChoices.get(player.getID());
            //send user choices to Roulette bets calc winner to determine payout
            for (int i = 0; i < userNums.size(); i ++) {
                long thisBet = player.getBet(i);
                int  multiplier = RouletteBets.calcWinner(winner,userNums.get(i),userChoice.get(i));

                if(multiplier >0){
                    System.out.println("Won +"+(thisBet*multiplier-thisBet)+" chips!");
                    player.addChips((int) (thisBet*multiplier));
                }
            }
            //clear players bets
            player.resetBets();
            userNumbers.remove(player.getID());
            userChoices.remove(player.getID());
        }
    }
    //TODO: this function is TOO large. break it up



    public void play() {
        RouletteTable.printRouletteTable();
        //Each player makes all their bets before the next player decides.
        for (Player player : players) {
            System.out.println(player.getName()+": "+player.getChips()+" chips");
            ArrayList<ArrayList<RouletteNum>> thisUsersNumbers= new ArrayList<>();
            ArrayList<Integer> thisUserChoices = new ArrayList<>();
            //get the first users bet, exit if the user presses enter or types exit

            String userIn;

            do {
                ArrayList<RouletteNum> thisUsersNumber = new ArrayList<>();
                System.out.println("Place Bet, Press enter to end turn:");
                int betHolder = 0;
                userIn = scanner.nextLine().trim();  // Use nextLine() to handle full user input immediately
                if (userIn.equals("exit") || userIn.isEmpty()) {
                    break;
                }
                //attempt to place a bet
                try {
                    int betAmount = Integer.parseInt(userIn);  // Assuming bet amount or identifier is entered
                    betHolder = betAmount;
                    boolean bet = player.placeBet(betAmount);

                    if (bet) {
                        System.out.println("(1)Single (2)Double (3)Triple (4)Quadruple");
                        System.out.println("(5)Sixes (6)Dozen (7)Column (8)First Five");
                        System.out.println("(9)First Half (10)Second Half (11)Red (12)Black");
                        System.out.println("(13)Odd (14)Even (0)Explain the bets again");
                        //user bet choice
                        userIn = scanner.nextLine().trim();
                        int userInt = Integer.parseInt(userIn);
                        if(userInt!=0)
                            RouletteTable.printBet(userInt);
                        if (userInt == 0) {
                            RouletteTable.printBets();
                            //return bet if user asked to see options again
                            player.addChips(betHolder);
                        }
                        //user bet numbers, wait for all users inputs if user has to input more than one num

                        else if (userInt == 2 || userInt == 4) {
                            System.out.println("Pick two numbers:");
                            userIn = scanner.nextLine().trim();
                            String[] inputs = userIn.split(" ");

                            // Limit the loop to process at most two numbers from the input
                            for (int i = 0; i < inputs.length && i < 2; i++) {
                                try {
                                    int num = Integer.parseInt(inputs[i]);
                                    thisUsersNumber.add(numbers.get(num));
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input: '" + inputs[i] + "' is not a number.");
                                    break;
                                } catch (IndexOutOfBoundsException e) {
                                    System.out.println("Invalid input: '" + inputs[i] + "' is not a valid roulette number.");
                                    break;
                                }
                            }
                        }
                        else if (userInt <8){
                            System.out.println("Pick your choice");
                            userIn = scanner.nextLine();
                            thisUsersNumber.add(numbers.get(Integer.parseInt(userIn)));
                        }
                        else{
                            thisUsersNumber.add(numbers.get(RouletteBets.handleOpenBets(userInt)));
                        }
                        if (RouletteBets.validBets(userInt, thisUsersNumber)) {
                            thisUserChoices.add(userInt);
                            thisUsersNumbers.add(thisUsersNumber);
                        }
                        else{
                            System.out.println("Invalid bet");
                            player.addChips(betAmount);
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input");
                    System.out.println(e.getMessage());
                    //return bet if exception thrown
                    player.addChips(betHolder);
                }
            } while (!userIn.equals("exit") && !userIn.isEmpty());
            userChoices.put(player.getID(), thisUserChoices);
            userNumbers.put(player.getID(), thisUsersNumbers);
        }
        calcWinner(spin());
    }

}
