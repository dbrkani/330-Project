import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * {@code Deck} is the container for {@code Card}s. Creates a 52 card deck, or more if required
 * Provides methods for dealing, and
 * reshuffles when the number of remaining cards is low
 */
public class Deck {
    /**
     * List of {@code Card}s in the deck.
     */
    private final List<Card> cards;
    /**
     * The number of decks to be used
     */
    int numDecks;
    /**
     * Constructs a new Deck with a standard size.
     */
    public Deck() {
        this.cards = new ArrayList<>();
        this.numDecks = 1;
        initializeDeck();

    }
    /**
     * Alternate constructor for dealing multiple decks.
     *
     * @param numDecks the number of 52-card decks to include in this Deck
     */
    public Deck(int numDecks) {
        this.cards = new ArrayList<>();
        this.numDecks = numDecks;
        for (int i = 0; i < this.numDecks; i++)
            initializeDeck();
    }
    /**
     * Initializes the deck by creating the standard 52 cards for each deck specified by {@code numDecks}
     * The deck is then shuffled
     */
    private void initializeDeck() {
        String[] suits = { "♥", "♦", "♣", "♠" };
        String[] ranks = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace" };

        for (String suit : suits) {

            for (String rank : ranks) {
                this.cards.add(new Card(rank, suit));
            }
        }
        Collections.shuffle(this.cards);
    }


    /**
     * Deals the top card from the deck. If the remaining cards are less than 30% of the total cards in all decks combined,
     * the deck is reshuffled
     *
     * @return the dealt Card
     */
    public Card dealCard() {
        if (cards.size() < (numDecks*52*.3)) {
            cards.clear();
            initializeDeck();
        }
        return this.cards.removeFirst();
    }
}
