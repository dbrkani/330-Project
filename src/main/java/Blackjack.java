import java.util.*;


/**
 * {@code Blackjack} extends {@code Games}, simulating blackjack. It manages the deck, player hands, dealer's hand,
 * and the overall game logic like dealing, betting, and calculating winners.
 */
public class Blackjack extends Games {

    /**
     * The {@code Deck} of cards used in the game.
     */

    private final Deck deck;
    /**
     * Maps each player's ID to their list of hands. Each player can have multiple hands due to splitting
     */
    private final Map<Integer, ArrayList<ArrayList<Card>>> hands;
    /**
     * List of cards for the dealer's hand
     */
    private final ArrayList<Card> dealerHand;
    /**
     * Constructs a new {@code Blackjack} game with the specified list of players
     * Initializes the deck and sets up empty maps and lists for player and dealer hands.
     *
     * @param players the list of players playing.
     */
    public Blackjack(ArrayList<Player> players) {
        super(players);
        //TODO: change deck to 8, test to make sure it doesnt break.
        this.deck = new Deck();
        this.hands = new HashMap<>();
        this.dealerHand = new ArrayList<>();
    }
    /**
     * Deals first two cards to each player and to the dealer at the start of the game.
     */
    public void dealInitialCards() {

        for (Player player : players) {
            ArrayList<Card> hand = new ArrayList<>();
            hand.add(deck.dealCard());
            hand.add(deck.dealCard());
            ArrayList<ArrayList<Card>> handContainer = new ArrayList<>();
            handContainer.add(hand);
            hands.put(player.getID(), handContainer);
        }

        dealerHand.add(deck.dealCard());
        dealerHand.add(deck.dealCard());

    }
    /**
     * Allows a player to double their bet and receive exactly one additional card.
     *
     * @param currentHand The hand that the player wants to double down on
     * @param player The player who is doubling down
     * @return The new card dealt to the player, or null player cant double down
     */
    public Card doubleDown(List<Card> currentHand, Player player) {
        int handIndex = hands.get(player.getID()).indexOf(currentHand);
        long bet = player.getBet(handIndex);
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


    /**
     * Allows a player to split their hand into two if the two cards are the same rank.
     *
     * @param currentHand The hand the player wants to split.
     * @param player      The player attempting the split.
     * @return An array of two integers indicating the value and suit of the card removed from the original hand, or null
     * if the player cannot split.
     */
    public int[] split(List<Card> currentHand, Player player) {
        int handIndex = hands.get(player.getID()).indexOf(currentHand);
        long bet = player.getBet(handIndex);
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
    /**
     * Calculates and prints the value of a hand
     *
     * @param hand The hand that's being calculated
     */
    public void printHand(List<Card> hand) {
        for (Card card : hand) {
            if (card.equals(hand.getLast()))
                System.out.println(card);
            else
                System.out.print(card+ ", ");
        }
    }

    /**
     * Gets the value of the hand. alters it if the player has aces and is in danger of going over
     *
     * @param card The card to add to the hand's value
     * @param value Current total value of the hand
     * @param aceCount Number of aces in the hand
     * @return An array with the new value and the new count of aces
     */

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
    /**
     * Executes the dealer's logic. The dealer will hit until their total reaches 17 or higher
     *
     * @return The final value of the dealer's hand.
     */
    public int dealerPlay() {
        dealerHand.getFirst();
        int value = 0;
        int aceCount = 0;

        for (Card card : dealerHand) {
            int[] results = getValue(card, value, aceCount);
            value = results[0];
            aceCount = results[1];
        }

        while (value < 17) {
            Card newCard = deck.dealCard();
            dealerHand.add(newCard);
            int[] results = getValue(newCard, value, aceCount);
            value = results[0];
            aceCount = results[1];
        }

        printHand(dealerHand);
        System.out.println("Dealer's final score: " + value);

        return value;
    }


    /**
     * Compares the final hand values of all players against the dealer to determine winners.
     *
     * @param endValues A map of each player and their hand values
     */

    public void calcWinner(HashMap<Player,ArrayList<Integer>>endValues) {
        int dealerValue = dealerPlay();
        Set<Player> players= endValues.keySet();
        for (Player player : players) {
            ArrayList<Integer> playerVals = endValues.get(player);
            for (Integer value : playerVals) {
                int valueIndex = playerVals.indexOf(value);
                long bet = player.getBet(valueIndex);
                if (value > dealerValue && value < 22||value<22 && dealerValue>21) {
                    System.out.println(player.getName()+" wins with " + value + " against dealer's " + dealerValue);
                    player.addChips((int) (bet*2));
                } else if(value==dealerValue && value < 22){
                    System.out.println(player.getName()+" breaks even with dealer at " + dealerValue);
                    player.addChips((int) bet);
                }

                else {
                    System.out.println("Dealer wins with " + dealerValue + " against "+player.getName()+"'s " + value);

            }
                player.resetBets();
        }

        }
        dealerHand.clear();
    }
    /**
     * Does a full round of Blackjack. Takes bets, deals cards, allowing players to act, and calculating the winner
     */
    public void play() {

        HashMap<Player,ArrayList<Integer>> endValues = new HashMap<>();


        //FIXME: players can currently bet 0 and still play.
        for (Player player : players) {
            player.placeBet();
        }
        dealInitialCards();
        System.out.println("Dealer showing a " + dealerHand.getFirst());

        for (Player player : players) {
            ArrayList<Integer> handVal= new ArrayList<>();

            ArrayList<ArrayList<Card>> playerHands = hands.get(player.getID());
            for (ArrayList<Card> hand : playerHands) {

                int value = 0;
                int aceCount = 0;
                for (Card card : hand) {

                    int[] results = getValue(card, value, aceCount);
                    value = results[0];
                    aceCount = results[1];
                }

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
                            switch (choice) {

                                case 1: {
                                    Card hitCard = deck.dealCard();
                                    hand.add(hitCard);
                                    int[] result = getValue(hitCard,value, aceCount);
                                    value = result[0];
                                    aceCount = result[1];
                                }
                                    break;
                                case 2: {

                                    printHand(hand);
                                    System.out.println("Standing at: " + value);
                                    handVal.add(value);
                                    break loop;
                                }
                                case 3:

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

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            scanner.next();
                        }
                    } else if (value == 21) {

                        System.out.println("Blackjack!");
                        handVal.add(value);
                        break;
                    } else {


                        if (aceCount==0) {
                            System.out.println("Bust!");
                            handVal.add(value);
                        }
                            break;

                    }
                }
            }
            endValues.put(player, handVal);
            System.out.println();
        }
        calcWinner(endValues);
          }



}



