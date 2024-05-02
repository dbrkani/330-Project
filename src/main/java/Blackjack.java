import java.util.*;

public class Blackjack extends Games {
    private Deck deck;
    private Map<String, ArrayList<Integer>> bets;
    private Map<String, ArrayList<ArrayList<Card>>> hands;
    private int initialBet;
    private int currentBet;
    private Scanner scanner;

    public Blackjack(ArrayList<Player> players) {
        super(players);
        this.deck = new Deck();
        this.hands = new HashMap<String, ArrayList<ArrayList<Card>>>();
        this.bets = new HashMap<String, ArrayList<Integer>>();
        this.scanner = new Scanner(System.in);
    }

    public void dealInitialCards() {
        ArrayList<Card> dealerHand = new ArrayList<Card>();
        for (Player player : players) {
            ArrayList<Card> hand = new ArrayList<Card>();
            hand.add(deck.dealCard());
            hand.add(deck.dealCard());
            ArrayList<ArrayList<Card>> handContainer = new ArrayList<ArrayList<Card>>();
            handContainer.add(hand);
            hands.put(player.getName(), handContainer);
        }
        dealerHand.add(deck.dealCard());
        dealerHand.add(deck.dealCard());
        ArrayList<ArrayList<Card>> container = new ArrayList<>();
        container.add(dealerHand);
        hands.put("Dealer", container);
    }


    public Card doubleDown(List<Card> currentHand, Player player) {

        int handIndex = hands.get(player.getName()).indexOf(currentHand);
        ArrayList<Integer> allBets = bets.get(player.getName());
        int bet = allBets.get(handIndex);
        int newBet = player.placeBet(bet);
        bet = bet * 2;
        if (currentHand.size() == 2 && newBet != 0) {
            allBets.set(handIndex, bet);
            bets.put(player.getName(), allBets);
            Card hitCard =deck.dealCard();
            currentHand.add(hitCard);
            return hitCard;
        } else {
            System.out.println("Can't Double Down");
            return null;
        }
    }

    public int[] split(List<Card> currentHand, Player player) {
        int handIndex = hands.get(player.getName()).indexOf(currentHand);
        ArrayList<Integer> allBets = bets.get(player.getName());
        int bet = allBets.get(handIndex);
        int newBet = player.placeBet(bet);

        if (currentHand.size() == 2 && currentHand.get(0).getRank().equals(currentHand.get(1).getRank()) && newBet != 0) {
            ArrayList<Card> splitHand = new ArrayList<Card>();
            Card card = currentHand.remove(1);
            int cardValue = card.getVal();
            int cardSuit = (cardValue==11?1:0);
            int[] cardVal = {cardValue,cardSuit};
            splitHand.add(card);
            currentHand.add(deck.dealCard());
            splitHand.add(deck.dealCard());
            hands.get(player.getName()).add(splitHand);
            allBets.set(handIndex + 1, newBet);
            System.out.println("Hands split. Additional bet placed.");
            return cardVal;
        } else {
            System.out.println("Split not allowed.");
            return null;
        }
    }

    public void printHand(List<Card> hand) {
        for (Card card : hand) {
            if (card.equals(hand.get(hand.size() - 1)))
                System.out.println(card.toString());
            else
                System.out.print(card.toString() + ", ");
        }
    }

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

    public int dealerPlay() {
        List<Card> dealerHand = hands.get("Dealer").getFirst();
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


    public void calcWinner(HashMap<Player,ArrayList<Integer>>endValues) {
        int dealerValue = dealerPlay();
        Set<Player> players= endValues.keySet();
        for (Player player : players) {
            ArrayList<Integer> playerVals = endValues.get(player);
            for (Integer value : playerVals) {
                if (value > dealerValue && value < 22) {
                    System.out.println(player.getName()+" wins with " + value + " against dealer's " + dealerValue);
                    int bet = bets.get(player.getName()).get(playerVals.indexOf(value));
                    player.addChips(bet*2);

                } else if(value==dealerValue && value < 22){
                    System.out.println(player.getName()+" breaks even with dealer at " + dealerValue);
                    int bet = bets.get(player.getName()).get(playerVals.indexOf(value));
                    player.addChips(bet);
                }

                else {
                    System.out.println("Dealer wins with " + dealerValue + " against player's " + value);

            }
        }

        }
    }

    public void play() {
        HashMap<Player,ArrayList<Integer>> endValues = new HashMap<>();
        for (Player player : players) {
            ArrayList<Integer> betList = new ArrayList<Integer>();
            betList.add(player.placeBet());
            bets.put(player.getName(), betList);
        }
        dealInitialCards();
        System.out.println("Dealer showing a " + hands.get("Dealer").getFirst().getFirst());

        for (Player player : players) {
            ArrayList<Integer> handVal= new ArrayList<>();
            // Get hands for the active player
            ArrayList<ArrayList<Card>> playerHands = hands.get(player.getName());
            for (ArrayList<Card> hand : playerHands) {
                int value = 0;
                int aceCount = 0;
                for (Card card : hand) {

                    int[] results = getValue(card, value, aceCount);
                    value = results[0];
                    aceCount = results[1];

                }
                // Process individual hand
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
                                        System.out.println("Doubled Down at: " + value);
                                        handVal.add(value);
                                        break loop;
                                    }
                                    break;
                                case 4:{
                                    // Split the hand
                                    int[] splitResult = split(hand, player);
                                    value -= splitResult[0];
                                    aceCount -= splitResult[1];
                                    break;
                                }
                                default:
                                    System.out.println("Incorrect choice, try again.");
                                    break;
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid input, please enter a valid number.");
                            scanner.next(); // this handles the invalid input
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
        }
        calcWinner(endValues);
          }



};



