import java.util.ArrayList;

/**
 * A utility class containing static methods to validate and calculate roulette bets.
 * This class cannot be instantiated and is made just for roulette bet management.
 */
public final class RouletteBets {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private RouletteBets() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Validates a single number bet.
     *
     * @param betNum The number on which the bet is placed.
     * @return true if the number is within the valid roulette range (0-37), false otherwise.
     */
    public static boolean singleValid(RouletteNum betNum) {
        return betNum.getNum() >= 0 && betNum.getNum() <= 37;
    }

    /**
     * Validates a double number bet, where the numbers must be adjacent on the roulette table.
     *
     * @param betNums A list containing two numbers for the double bet.
     * @return true if the numbers are valid doubles, false otherwise.
     */
    public static boolean doublesValid(ArrayList<RouletteNum> betNums) {
        return Math.abs(betNums.getFirst().getNum() - betNums.getLast().getNum()) == 1 ||
                Math.abs(betNums.getFirst().getNum() - betNums.getLast().getNum()) == 3;
    }

    /**
     * Validates a quad bet where the selected numbers must form a square on the roulette table.
     *
     * @param betNums A list containing four numbers for the quad bet.
     * @return true if the numbers form a valid quad, false otherwise.
     */
    public static boolean quadsValid(ArrayList<RouletteNum> betNums) {
        return Math.abs(betNums.getLast().getNum() - betNums.getFirst().getNum()) == 4;
    }

    /**
     * Validates a six line bet, which requires 2 consecutive rows for betting.
     *
     * @param betNum A single roulette number to validate.
     * @return true if the number is valid for a six line bet, false otherwise.
     */
    public static boolean sixesValid(RouletteNum betNum) {
        return betNum.getNum() < 34;
    }

    /**
     * Validates a dozen bet, placed on the beginning of the first, second, or last third of the roulette table.
     *
     * @param betNum The number representing the dozen section.
     * @return true if the bet is placed on a valid dozen, false otherwise.
     */
    public static boolean dozenValid(RouletteNum betNum) {
        return betNum.getNum() == 1 || betNum.getNum() == 13 || betNum.getNum() == 25;
    }

    /**
     * Handles non-number specific bets during testing by returning a valid integer.
     *
     * @param betPlaced The type of bet placed.
     * @return An integer representing a valid outcome for the bet type.
     */
    public static int handleOpenBets(int betPlaced) {
        return switch (betPlaced) {
            case 8, 9, 11, 13 -> 1;
            case 10 -> 19;
            case 12, 14 -> 2;
            default -> throw new IllegalArgumentException("Invalid bet type");
        };
    }

    /**
     * Handles checking the validity of bets based on the previous rules.
     *
     * @param betPlaced The type of bet placed.
     * @param betNums   A list of RouletteNum objects representing the numbers bet on.
     * @return true if the bets are valid according to the game rules, false otherwise.
     */
    public static boolean validBets(int betPlaced, ArrayList<RouletteNum> betNums) {
        for (RouletteNum betNum : betNums) {
            if (betNum.getNum() < 0 || betNum.getNum() > 37) {
                return false;
            }
        }
        return switch (betPlaced) {
            case 1 -> singleValid(betNums.getFirst());
            case 2 -> doublesValid(betNums);
            case 4 -> quadsValid(betNums);
            case 5 -> sixesValid(betNums.getFirst());
            case 6 -> dozenValid(betNums.getFirst());
            default -> true;
        };
    }

    /**
     * Calculates the winner payout multiplier based on the bet type and the outcome.
     * The method returns 0 if the bet is lost.
     *
     * @param num       The RouletteNum object representing the winning number.
     * @param betNums   A list of RouletteNum objects representing the numbers bet on.
     * @param betPlaced The type of bet placed.
     * @return An integer multiplier for the payout if the bet wins, 0 if it loses.
     */
    public static int calcWinner(RouletteNum num, ArrayList<RouletteNum> betNums, int betPlaced) {
        return switch (betPlaced) {
            case 1 -> singlesWinner(num, betNums);
            case 2 -> doublesWinner(num, betNums);
            case 3 -> triplesWinner(num, betNums);
            case 4 -> quadsWinner(num, betNums);
            case 5 -> sixesWinner(num, betNums);
            case 6 -> dozenWinner(num, betNums);
            case 7 -> columnWinner(num, betNums);
            case 8 -> firstFiveWinner(num);
            case 9 -> firstHalfWinner(num);
            case 10 -> secondHalfWinner(num);
            case 11, 12 -> colorWinner(num, betNums);
            case 13, 14 -> oddEvenWinner(num, betNums);
            default -> throw new IllegalStateException("Unexpected value: " + betPlaced);
        };
    }

    /**
     * Calculates the payout for a single number bet.
     *
     * @param num The winning number.
     * @param betNums A list of numbers bet on, only the first is considered for a single bet.
     * @return Payout multiplier if the bet is a winner, 0 otherwise
     */
    public static int singlesWinner(RouletteNum num, ArrayList<RouletteNum> betNums) {
        return num.getNum() == betNums.getFirst().getNum() ? 36 : 0;
    }

    /**
     * Calculates the payout for a double street bet (two consecutive numbers).
     *
     * @param num The winning number.
     * @param betNums A list containing two numbers for the bet.
     * @return Payout multiplier if the bet is a winner, 0 otherwise
     */
    public static int doublesWinner(RouletteNum num, ArrayList<RouletteNum> betNums) {
        return num.getNum() == betNums.getFirst().getNum() || num.getNum() == betNums.getLast().getNum() ? 18 : 0;
    }

    /**
     * Calculates the payout for a street bet (three numbers in a row).
     *
     * @param num The winning number.
     * @param betNums A list of numbers bet on, only the row of the first number is considered.
     * @return Payout multiplier if the bet is a winner, 0 otherwise
     */
    public static int triplesWinner(RouletteNum num, ArrayList<RouletteNum> betNums) {
        return num.getRow() == betNums.getFirst().getRow() ? 12 : 0;
    }

    /**
     * Calculates the payout for a corner bet (four numbers in a square).
     *
     * @param num The winning number.
     * @param betNums A list of numbers forming the square's corners.
     * @return Payout multiplier if the bet is a winner, 0 otherwise
     */
    public static int quadsWinner(RouletteNum num, ArrayList<RouletteNum> betNums) {
        int number = num.getNum();
        int firstBet = betNums.getFirst().getNum();
        int lastBet = betNums.getLast().getNum();
        return (number == firstBet || number == lastBet || number == firstBet + 1 || number == lastBet - 1) ? 9 : 0;
    }

    /**
     * Calculates the payout for a six line bet (six numbers in two adjacent rows).
     *
     * @param num The winning number.
     * @param betNums A list of numbers, only the row of the first number is considered.
     * @return Payout multiplier if the bet is a winner, 0 otherwise
     */
    public static int sixesWinner(RouletteNum num, ArrayList<RouletteNum> betNums) {
        int row = num.getRow();
        int betRow = betNums.getFirst().getRow();
        return row >= betRow && row <= betRow + 1 ? 6 : 0;
    }

    /**
     * Calculates the payout for a dozen bet (twelve numbers in one of the three dozens).
     *
     * @param num The winning number.
     * @param betNums A list of numbers, only the row of the first number is considered.
     * @return Payout multiplier if the bet is a winner, 0 otherwise
     */
    public static int dozenWinner(RouletteNum num, ArrayList<RouletteNum> betNums) {
        int row = num.getRow();
        int betRow = betNums.getFirst().getRow();
        return row >= betRow && row <= betRow + 3 ? 3 : 0;
    }

    /**
     * Calculates the payout for a column bet (all numbers in a single column).
     *
     * @param num The winning number.
     * @param betNums A list of numbers, only the column of the first number is considered.
     * @return Payout multiplier if the bet is a winner, 0 otherwise
     */
    public static int columnWinner(RouletteNum num, ArrayList<RouletteNum> betNums) {
        return num.getColumn() == betNums.getFirst().getColumn() ? 3 : 0;
    }

    /**
     * Calculates the payout for the first five numbers bet.
     *
     * @param num The winning number.
     * @return Payout multiplier if the bet is a winner, 0 otherwise
     */
    public static int firstFiveWinner(RouletteNum num) {
        int number = num.getNum();
        return (number == 0 || number == 1 || number == 2 || number == 3 || number == 37) ? 7 : 0;
    }

    /**
     * Calculates the payout for bets on the first half of the numbers (1-18).
     *
     * @param num The winning number.
     * @return Payout multiplier if the bet is a winner, 0 otherwise
     */
    public static int firstHalfWinner(RouletteNum num) {
        return num.getNum() <= 18 && num.getNum() > 0 ? 2 : 0;
    }

    /**
     * Calculates the payout for bets on the second half of the numbers (19-36).
     *
     * @param num The winning number.
     * @return Payout multiplier if the bet is a winner, 0 otherwise
     */
    public static int secondHalfWinner(RouletteNum num) {
        return num.getNum() >= 19 && num.getNum() <= 36 ? 2 : 0;
    }

    /**
     * Calculates the payout for bets on a specific color.
     *
     * @param num   The winning number.
     * @param color A list of numbers, only the color of the first number is considered.
     * @return Payout multiplier if the bet is a winner, 0 otherwise
     */
    public static int colorWinner(RouletteNum num, ArrayList<RouletteNum> color) {
        return num.getColor() == color.getFirst().getColor() ? 2 : 0;
    }

    /**
     * Calculates the payout for bets on odd or even numbers.
     *
     * @param num     The winning number.
     * @param oddEven A list of numbers, only the parity of the first number is considered.
     * @return Payout multiplier if the bet is a winner, 0 otherwise
     */
    public static int oddEvenWinner(RouletteNum num, ArrayList<RouletteNum> oddEven) {
        return num.getNum() % 2 == oddEven.getFirst().getNum() % 2 ? 2 : 0;
    }

}