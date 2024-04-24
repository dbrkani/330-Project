import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
  private List<Card> cards;
  int numDecks;

  public Deck() {
    this.cards = new ArrayList<Card>();
    this.numDecks = 1;
    initializeDeck();
    shuffle();
  }

  public Deck(int numDecks) {
    this.cards = new ArrayList<Card>();
    this.numDecks = numDecks;
    for (int i = 0; i < this.numDecks; i++)
      initializeDeck();
    shuffle();
  }

  private void initializeDeck() {
    String[] suits = { "♥", "♦", "♣", "♠" };
    String[] ranks = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace" };

    for (String suit : suits) {
      for (String rank : ranks) {
        this.cards.add(new Card(rank, suit));
      }
    }
  }

  public void shuffle() {
    if(cards.size() < (numDecks*52*.3)){
      cards.clear();
      for (int i = 0; i <numDecks;i++)
        initializeDeck();
    }

    Collections.shuffle(this.cards);
  }



  public Card dealCard() {
    if (!this.cards.isEmpty()) {
      return this.cards.remove(0);
    }
    return null;
  }
}
