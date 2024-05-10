public class Card {
    private final String suit;
    private final String rank;
    private int value;

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

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    public void setVal(int val){
        value = val;
    }

    public int getVal(){
        return value;
    }

    public String toString() {
        return rank + " of " + suit;
    }
}
