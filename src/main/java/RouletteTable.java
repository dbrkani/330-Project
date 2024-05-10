import java.util.Arrays;
public class RouletteTable {
    //TODO: not necessary if using a front end
    //fun lil colors for the console.
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    //TODO: not necessary if using a front end
    //List that holds number colors
    public static final int[] RED_NUMBERS = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
    public static final int[] BLACK_NUMBERS = {2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35};

    //Enum that holds bet multipliers and payout descriptions.
    public enum BetType {
        SINGLE(36, "Single number bet, Self Explanatory"),
        DOUBLE(18, "Double number bet, Bet on two adjacent numbers"),
        TRIPLE(12, "Three number bet, Bet on a whole row. Pick a number in a row"),
        QUADRUPLE(9, "Four number bet, Bet on four adjacent numbers, Pick Top left an bottom right numbers"),
        FIVES(7, "Five number bet, Bet on 0-00-1-2-3."),
        SIXES(6, "Six number bets, Bet on two adjacent rows. Pick a number in the top row."),
        DOZEN(3, "Twelve number bets, Bet on four rows. Pick 1, 13, or 25"),
        COLUMN(3, "Column bet,Pick a number in a column."),
        HALF1(2, "Bet on first half (1-18)."),
        HALF2(2, "Bet on second half (19-36)"),
        COLOR(2, "Red or black"),
        ODDEVEN(2, "Odd or even bets, pays even money.");


        private final int payoutMultiplier;
        private final String description;
//constructor for enum
        BetType(int payoutMultiplier, String description) {
            this.payoutMultiplier = payoutMultiplier;
            this.description = description;
        }
        public int getPayoutMultiplier() {
            return payoutMultiplier;
        }

        public String getDescription() {
            return description;
        }
    }

    public static void printBets() {
        System.out.println("Available Roulette Bets and Payouts:");
        for (BetType bet : BetType.values()) {
            System.out.println(bet.name() + ": "+ bet.getPayoutMultiplier() + " to 1  " + bet.getDescription() );
        }
    }

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

        // Print the numbers rotated
        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < numbers.length; row++) {
                String number;
                if (col < numbers[row].length) {

                    if (numbers[row][col].trim().equals("0")|| numbers[row][col].trim().equals("00")) {
                        number = ANSI_GREEN + numbers[row][col]  + ANSI_RESET;
                    }
                    else if ( Arrays.binarySearch(RED_NUMBERS,Integer.parseInt(numbers[row][col].trim()))>=0) {
                         number = ANSI_RED + numbers[row][col]  + ANSI_RESET;
                    }
                    else {
                         number = ANSI_BLACK + numbers[row][col]  + ANSI_RESET;

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

