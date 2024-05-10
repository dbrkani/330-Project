import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//creates a deck that generates deals, and holds cards.
public class Deck {
    private final List<Card> cards;
    int numDecks;

    public Deck() {
        this.cards = new ArrayList<>();
        this.numDecks = 1;
        initializeDeck();
        shuffle();
    }
//different games use different numbers of decks. number of decks parameter is good future proofing for new games
    public Deck(int numDecks) {
        this.cards = new ArrayList<>();
        this.numDecks = numDecks;
        for (int i = 0; i < this.numDecks; i++)
            initializeDeck();
        shuffle();
    }

    private void initializeDeck() {
        String[] suits = { "♥", "♦", "♣", "♠" };
        String[] ranks = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace" };
//for each suit
        for (String suit : suits) {
            //insert one rank
            for (String rank : ranks) {
                this.cards.add(new Card(rank, suit));
            }
        }
    }

 //TODO: shuffle should be refactored and incorporated into dealcard
// reinitialize deck if
    public void shuffle() {
        if(cards.size() < (numDecks*52*.3)){
            cards.clear();
            for (int i = 0; i <numDecks;i++)
                initializeDeck();
        }

        Collections.shuffle(this.cards);
    }


    // remove the first card in a deck, then return it
    public Card dealCard() {
        if (!this.cards.isEmpty()) {
            return this.cards.removeFirst();
        }
        return null;
    }
}
