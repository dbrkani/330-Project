import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
/* utility class to manage bet validity and bet payout. those must be separate due to user placing multiple bets
before wheel is spun
 */
public final class RouletteBets {
    private final ArrayList<RouletteNum> numbers = new ArrayList<>();

    private RouletteBets() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

//validity for bets where user has to pick a number
    public static boolean singleValid(RouletteNum betNum) {
        return betNum.getNum() >= 0 && betNum.getNum() <= 37;
    }

    public static boolean doublesValid(ArrayList<RouletteNum> betNums) {
        return Math.abs(betNums.getFirst().getNum() - betNums.getLast().getNum()) == 1 || Math.abs(betNums.getLast().getNum() - betNums.getFirst().getNum()) == 3;
    }

    public static boolean quadsValid(ArrayList<RouletteNum> betNums) {
        return Math.abs(betNums.getLast().getNum() - betNums.getFirst().getNum()) == 4;
    }

    public static boolean sixesValid(RouletteNum betNum) {
        return betNum.getNum() < 34;
    }

    public static boolean dozenValid(RouletteNum betNum) {
        return betNum.getNum() == 1 || betNum.getNum() == 13 || betNum.getNum() == 25;
    }
//assigning a valid integer to return for non number bets. probably irrelevant but good to be safe during testing.
    public static int handleOpenBets(int betPlaced) {

        int result = switch (betPlaced) {
            case 8, 9, 11, 13 -> 1;
            case 10 -> 19;
            case 12, 14 -> 2;
            default -> throw new IllegalArgumentException();
        };
        return result;
    }

//switch case for checking valid bets. cases determined by user input and bet list.
    public static boolean validBets(int betPlaced, ArrayList<RouletteNum> betNums) {
        boolean result = switch (betPlaced) {
            case 1 -> singleValid(betNums.getFirst());
            case 2 -> doublesValid(betNums);
            case 4 -> quadsValid(betNums);
            case 5 -> sixesValid(betNums.getFirst());
            case 6 -> dozenValid(betNums.getFirst());
            default -> true;
        };
        return result;

    }

    //TODO: most of these can be an inline return, but easier readability this way.

    //return winner payout multiplier, set to 0 if loss
    public static int calcWinner(RouletteNum num, ArrayList<RouletteNum> betNums, int betPlaced) {
        int result= switch (betPlaced) {
            //single
            case 1 -> singlesWinner(num,betNums);
            //double
            case 2 -> doublesWinner(num,betNums);
            //row
            case 3 -> triplesWinner(num,betNums);
            //square
            case 4 -> quadsWinner(num,betNums);
            //sixes
            case 5 -> sixesWinner(num,betNums);
            //fourRows
            case 6 -> dozenWinner(num,betNums);
            //column
            case 7 -> columnWinner(num,betNums);
            //first five
            case 8 -> firstFiveWinner(num,betNums);
            //first half
            case 9 -> firstHalfWinner(num);
            //second half
            case 10-> secondHalfWinner(num);
            //Color
            case 11,12 ->colorWinner(num,betNums);
            //odd or even
            case 13,14 ->oddEvenWinner(num,betNums);

            default -> throw new IllegalStateException("Unexpected value: " + betPlaced);
        };
        return result;




    }

    public static int singlesWinner(RouletteNum num, ArrayList<RouletteNum> betNums) {
        return num.getNum() == betNums.getFirst().getNum()?36:0;
    }

    public static int doublesWinner(RouletteNum num, ArrayList<RouletteNum> betNums) {
        return num.getNum() == betNums.getFirst().getNum() || num.getNum() == betNums.getLast().getNum()?18:0;
    }

    public static int triplesWinner(RouletteNum num, ArrayList<RouletteNum> betNums) {
        return num.getRow() == betNums.getFirst().getRow()?12:0;
    }
    public static int quadsWinner(RouletteNum num, ArrayList<RouletteNum> betNums) {
        int number = num.getNum();
        int firstBet = betNums.getFirst().getNum();
        int lastBet = betNums.getLast().getNum();
        return (number == firstBet || number == lastBet || number == firstBet + 1 || number == lastBet - 1)?9:0;
    }
    public static int sixesWinner(RouletteNum num, ArrayList<RouletteNum> betNums) {
        int row = num.getRow();
        int betRow = betNums.getFirst().getRow();
        return row >= betRow && row <= betRow + 1?6:0;
    }
    public static int dozenWinner(RouletteNum num, ArrayList<RouletteNum> betNums) {
        int row = num.getRow();
        int betRow = betNums.getFirst().getRow();
        return row >= betRow && row <= betRow + 3?3:0;
    }

    public static int columnWinner(RouletteNum num, ArrayList<RouletteNum> betNums) {
        return num.getColumn() == betNums.getFirst().getColumn()?3:0;
    }

    public static int firstFiveWinner(RouletteNum num, ArrayList<RouletteNum> betNums) {
        int number = num.getNum();
        return(number ==0||number==1||number==2||number==3||number==37)?7:0;
    }

    public static int firstHalfWinner(RouletteNum num) {
        return num.getNum() <= 18 && num.getNum() > 0?2:0;
    }

    public static int secondHalfWinner(RouletteNum num) {
        return num.getNum() <= 36 && num.getNum() >= 19?2:0;
    }

    public static int colorWinner(RouletteNum num, ArrayList<RouletteNum> color) {
        return num.getColor() == color.getFirst().getColor()?2:0;
    }

    public static int oddEvenWinner(RouletteNum num, ArrayList<RouletteNum> oddEven) {
        return num.getNum() % 2 == oddEven.getFirst().getNum() % 2?2:0;
    }

}