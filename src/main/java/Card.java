/**
 * {@code Card} represents a single playing card from a standard deck, including suit, rank, and value
 */

public class Card {
    /**
     * Card Suit
     */
    private final String suit;
    /**
     * Card rank
     */
    private final String rank;
    /**
     * Card value
     */
    private int value;

    /**
     * Constructs a new card with the specified rank and suit, then determines the value
     * gets parameters from {@code Deck}
     * @param rank the rank of the card
     * @param suit the suit of the card
     */

    public Card(String rank, String suit) {
        this.suit = suit;
        this.rank = rank;

        // generates card value
        if(Character.isDigit(rank.charAt(0)))
            this.value = Integer.parseInt(rank);
        else if (this.rank.equals("Ace"))
            this.value = 11;
        else
            this.value = 10;

    }
    /**
     * Returns the suit of the card
     *
     * @return the suit of the card
     */
    public String getSuit() {
        return suit;
    }

    /**
     * Returns the rank of the card
     *
     * @return the rank of the card
     */
    public String getRank() {
        return rank;
    }
    /**
     * Sets the value of the card. used to adjust aces
     *
     * @param val the new value to set for the card
     */
    public void setVal(int val){
        value = val;
    }
    /**
     * Returns the value of the card
     *
     * @return the value of the card
     */
    public int getVal(){
        return value;
    }
    /**
     * Returns a string representation of the card, combining its rank and suit
     *
     * @return a string representing the card
     */
    public String toString() {
        return rank + suit;
    }
}
