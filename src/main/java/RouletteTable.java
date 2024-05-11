import java.util.Arrays;

/**
 * {@code RouletteTable} simulates the layout and functionality of a roulette table.
 * It provides methods to print the betting options, roulette numbers, and their colors.
 */


public class RouletteTable {
    /**
     * Fun lil colors for the console.
     */
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    /**
     * Array holding the numbers that are red on a roulette wheel. able to label other colors with just this list
     */
    public static final int[] RED_NUMBERS = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
   // public static final int[] BLACK_NUMBERS = {2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35};

    /**
     * Enum of the types of bets available on a roulette table with their payout multipliers and descriptions.
     */
    public enum BetType {
        SINGLE(36, "Single number bet, Self Explanatory"),
        DOUBLE(18, "Double number bet, Bet on two adjacent numbers"),
        TRIPLE(12, "Three number bet, Bet on a whole row. Pick a number in a row"),
        QUADRUPLE(9, "Four number bet, Bet on four adjacent numbers, Pick Top left an bottom right numbers"),
        SIXES(6, "Six number bets, Bet on two adjacent rows. Pick a number in the top row."),
        DOZEN(3, "Twelve number bets, Bet on four rows. Pick 1, 13, or 25"),
        COLUMN(3, "Column bet,Pick a number in a column."),
        FIVES(7, "Five number bet, Bet on 0-00-1-2-3."),
        HALF1(2, "Bet on first half (1-18)."),
        HALF2(2, "Bet on second half (19-36)"),
        COLOR(2, "Red or black"),
        ODDEVEN(2, "Odd or even bets, pays even money.");


        private final int payoutMultiplier;
        private final String description;

        /**
         * Constructs a new bet type with a specified payout multiplier and description.
         *
         * @param payoutMultiplier the multiplier for the bet payout
         * @param description the description of the bet type
         */

        BetType(int payoutMultiplier, String description) {
            this.payoutMultiplier = payoutMultiplier;
            this.description = description;
        }

        /**
         * Returns the payout multiplier for the bet type.
         *
         * @return the payout multiplier
         */

        public int getPayoutMultiplier() {
            return payoutMultiplier;
        }
        /**
         * Returns the description of the bet type.
         *
         * @return the description
         */
        public String getDescription() {
            return description;
        }
    }
    /**
     * Prints a list of all roulette bets along with their payout multipliers and descriptions.
     */
    public static void printBets() {
        for (BetType bet : BetType.values()) {
            System.out.println(bet.getPayoutMultiplier() + " to 1:  " + bet.getDescription() );
            System.out.println();
        }
    }
    /**
     * Prints the specific roulette bet based on an index value, displaying the bet type, payout multiplier, and description.
     *
     * @param idx the index of the bet type
     */
    public static void printBet(int idx) {
        BetType bet;
        System.out.println("Available Roulette Bets and Payouts:");

        if (idx > 0 && idx <11)
            bet = BetType.values()[idx - 1];
        else if (idx ==11||idx ==12)
            bet = BetType.COLOR;
        else
            bet = BetType.ODDEVEN;
        System.out.println(bet.name() + ": "+ bet.getPayoutMultiplier() + " to 1  " + bet.getDescription() );
    }
    /**
     * Prints the roulette table with numbers and their corresponding colors, using color codes for the console.
     * This method visualizes the layout of a roulette table.
     */

    public static void printRouletteTable() {
        String[][] numbers = {
                {"  0  ", " 00  "},
                {"  1  ", "  2  ", "  3  "},
                {"  4  ", "  5  ", "  6  "},
                {"  7  ", "  8  ", "  9  "},
                {" 10  ", " 11  ", " 12  "},
                {" 13  ", " 14  ", " 15  "},
                {" 16  ", " 17  ", " 18  "},
                {" 19  ", " 20  ", " 21  "},
                {" 22  ", " 23  ", " 24  "},
                {" 25  ", " 26  ", " 27  "},
                {" 28  ", " 29  ", " 30  "},
                {" 31  ", " 32  ", " 33  "},
                {" 34  ", " 35  ", " 36  "}
        };

        String[] bets = {"1-18", "EVEN", " RED", "BLACK", "ODD ", "19-36"};
        for (int col = 0; col < 3; col++) {
            for (String[] strings : numbers) {
                String number;
                if (col < strings.length) {

                    if (strings[col].trim().equals("0") || strings[col].trim().equals("00")) {
                        number = ANSI_GREEN + strings[col] + ANSI_RESET;
                    } else if (Arrays.binarySearch(RED_NUMBERS, Integer.parseInt(strings[col].trim())) >= 0) {
                        number = ANSI_RED + strings[col] + ANSI_RESET;
                    } else {
                        number = ANSI_BLACK + strings[col] + ANSI_RESET;

                    }
                    System.out.printf("|%-5s", number);
                } else {
                    System.out.print("|     "); // Fill space if no element
                }
            }
            System.out.println("|");
        }

        // Print the bets horizontally at the bottom
        for (String bet : bets) {
            System.out.printf("|%-5s", bet);
        }
        System.out.println("|");
    }
}

