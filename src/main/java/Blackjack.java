import java.util.*;
public class Blackjack extends Games {
    private final Deck deck;
    private final Map<Integer, ArrayList<ArrayList<Card>>> hands;
    private final Scanner scanner;
    private final ArrayList<Card> dealerHand;

    public Blackjack(ArrayList<Player> players) {
        super(players);
        //TODO: change deck to 8, test to make sure it doesnt break.
        this.deck = new Deck();
        this.hands = new HashMap<>();
        this.scanner = new Scanner(System.in);
        this.dealerHand = new ArrayList<>();
    }

    public void dealInitialCards() {
//give each player two cards
        for (Player player : players) {
            ArrayList<Card> hand = new ArrayList<>();
            hand.add(deck.dealCard());
            hand.add(deck.dealCard());
            ArrayList<ArrayList<Card>> handContainer = new ArrayList<>();
            handContainer.add(hand);
            hands.put(player.getID(), handContainer);
        }
        //create dealer starting hand
        dealerHand.add(deck.dealCard());
        dealerHand.add(deck.dealCard());

    }

// check if player has enough money, then deal a card
    public Card doubleDown(List<Card> currentHand, Player player) {
        int handIndex = hands.get(player.getID()).indexOf(currentHand);
        int bet = player.getBet(handIndex);
        boolean placeBet= player.placeBet(handIndex,bet);
        if (currentHand.size() == 2 && placeBet) {
            Card hitCard =deck.dealCard();
            currentHand.add(hitCard);
            return hitCard;
        } else {
            System.out.println("Can't Double Down");
            return null;
        }
    }


    /*
    check if player has enough money, then remove card from hand and put it in a new hand. deal a card to each hand.

     */
    public int[] split(List<Card> currentHand, Player player) {
        int handIndex = hands.get(player.getID()).indexOf(currentHand);
        int bet = player.getBet(handIndex);
        boolean newBet = player.placeBet(bet);

        if (currentHand.size() == 2 && currentHand.get(0).getRank().equals(currentHand.get(1).getRank()) && newBet) {
            ArrayList<Card> splitHand = new ArrayList<>();
            Card card = currentHand.remove(1);
            int cardValue = card.getVal();
            int cardSuit = (cardValue==11?1:0);
            int[] cardVal = {cardValue,cardSuit};
            splitHand.add(card);
            currentHand.add(deck.dealCard());
            splitHand.add(deck.dealCard());
            hands.get(player.getID()).add(splitHand);
            System.out.println("Hands split. Additional bet placed.");
            return cardVal;
        } else {
            System.out.println("Split not allowed.");
            return null;
        }
    }

    public void printHand(List<Card> hand) {
        for (Card card : hand) {
            if (card.equals(hand.get(hand.size()-1)))
                System.out.println(card);
            else
                System.out.print(card+ ", ");
        }
    }
//check if hand is over 21, if there are aces in the hand, change one of its value to 1.
    public int[] getValue(Card card, int value, int aceCount) {
        int val = value + card.getVal();
        if (card.getRank().equals("Ace"))
            aceCount++;
        if (val > 21 && aceCount > 0) {
            val -= 10;
            aceCount--;
        }

        return new int[]{val, aceCount};

    }
    //TODO: maybe can use this to create bot players
    //logic for CPU hand
    public int dealerPlay() {
        dealerHand.get(0);
        int value = 0;
        int aceCount = 0;


        // Calculate initial value of dealer's hand
        for (Card card : dealerHand) {
            int[] results = getValue(card, value, aceCount);
            value = results[0];
            aceCount = results[1];
        }

        // Dealer hits until their score is 17 or more
        while (value < 17) {
            Card newCard = deck.dealCard();
            dealerHand.add(newCard);
            int[] results = getValue(newCard, value, aceCount);
            value = results[0];
            aceCount = results[1];
        }

        printHand(dealerHand); // Assuming there's a method to print the hand
        System.out.println("Dealer's final score: " + value);

        return value;
    }

/*check each players hand, lose if over 21
if under 21,
win if its better than the dealer, split if its equal to the dealer and
 */

    //TODO: change logic so a bust is checked first
    public void calcWinner(HashMap<Player,ArrayList<Integer>>endValues) {
        int dealerValue = dealerPlay();
        Set<Player> players= endValues.keySet();
        for (Player player : players) {
            ArrayList<Integer> playerVals = endValues.get(player);
            for (Integer value : playerVals) {
                int valueIndex = playerVals.indexOf(value);
                int bet = player.getBet(valueIndex);
                if (value > dealerValue && value < 22||value<22 && dealerValue>21) {
                    System.out.println(player.getName()+" wins with " + value + " against dealer's " + dealerValue);
                    player.addChips(bet*2);
                } else if(value==dealerValue && value < 22){
                    System.out.println(player.getName()+" breaks even with dealer at " + dealerValue);
                    player.addChips(bet);
                }

                else {
                    System.out.println("Dealer wins with " + dealerValue + " against "+player.getName()+"'s " + value);

            }
                player.resetBets();
        }

        }
        dealerHand.clear();
    }

    public void play() {
        //container for final values of each hand
        HashMap<Player,ArrayList<Integer>> endValues = new HashMap<>();

        //players place bets and get dealt cards.
        //FIXME: players can currently bet 0 and still play.
        for (Player player : players) {
            player.placeBet();
        }
        dealInitialCards();
        System.out.println("Dealer showing a " + dealerHand.get(0));

        for (Player player : players) {
            ArrayList<Integer> handVal= new ArrayList<>();
            // Get hands for the active player
            ArrayList<ArrayList<Card>> playerHands = hands.get(player.getID());
            for (ArrayList<Card> hand : playerHands) {
                //calculate value for each hand.
                int value = 0;
                int aceCount = 0;
                for (Card card : hand) {

                    int[] results = getValue(card, value, aceCount);
                    value = results[0];
                    aceCount = results[1];

                }
                // process individual hand
                loop:
                while (true) {
                    System.out.println(player.getName());
                    printHand(hand);
                    System.out.println("Score: " + value);

                    if (value < 21) {
                        System.out.println("1: Hit");
                        System.out.println("2: Stand");
                        System.out.println("3: Double Down");
                        System.out.println("4: Split");

                        try {
                            int choice = scanner.nextInt();
                            //TODO: can probably be its own function.
                            switch (choice) {
                                //hit - deal card and update value. loop
                                case 1: {
                                    Card hitCard = deck.dealCard();
                                    hand.add(hitCard);
                                    int[] result = getValue(hitCard,value, aceCount);
                                    value = result[0];
                                    aceCount = result[1];
                                }
                                    break;
                                case 2: {
                                    //stand - send hand value to container and end.
                                    printHand(hand);
                                    System.out.println("Standing at: " + value);
                                    handVal.add(value);
                                    break loop;
                                }
                                case 3:
                                    // double down - try to bet, hit, and stand. loop otherwise.
                                    Card doubleDownCard = doubleDown(hand, player);
                                    if (doubleDownCard != null) {
                                        int[] doubleResult = getValue(doubleDownCard,value, aceCount);
                                        value = doubleResult[0];
                                        printHand(hand);
                                        System.out.println("Doubled Down for a total of: " + value);
                                        handVal.add(value);
                                        break loop;
                                    }
                                    break;
                                case 4:{
                                    // Split - remove split card value
                                    int[] splitResult = split(hand, player);
                                    if (splitResult!=null){
                                        value -= splitResult[0];
                                        aceCount -= splitResult[1];
                                    }
                                    break;
                                }
                                default:
                                    System.out.println("Incorrect choice, try again.");
                                    break;
                            }
                            //TODO: make a better exception.
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            scanner.next(); // this handles the invalid input
                        }
                    } else if (value == 21) {
                        //if blackjack, break loop
                        System.out.println("Blackjack!");
                        handVal.add(value);
                        break;
                    } else {
                        //if bust, break loop
                        //TODO: check if ace count is required.
                        if (aceCount==0) {
                            System.out.println("Bust!");
                            handVal.add(value);
                        }
                            break;

                    }
                }
            }
            endValues.put(player, handVal);
        }
        calcWinner(endValues);
          }



}



