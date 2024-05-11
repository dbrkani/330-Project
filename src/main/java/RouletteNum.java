import java.util.Arrays;

/**
 * {@code RouletteNum} represents a single number on a roulette table, including its numeric value,
 * color, row, and column placement.
 */
public class RouletteNum {
    /**
     * The numeric value of the roulette number
     */
    private final int num;

    /**
     * The color of the number: 0 for green, 1 for red, and 2 for black
     */

    private final int color;
    /**
     * This numbers associated row
     */

    private final int row;
    /**
     * This numbers associated column
     */
    private final int column;

    /**
     * String representation of number, mainly used to deal with "00" being the fill in for 37
     */

    String numString;

    /**
     * Constructs a new {@code RouletteNum} with the specified number.
     * Initializes the number's string representation, row, column, and color
     *
     * @param num the numeric value of the roulette number
     */

    public RouletteNum(int num){
        this.num = num;
        this.numString = (num == 37 ? "00" : Integer.toString(num));
        this.row = ((num==0||num==37)?0:(int)Math.ceil(num/3.0));
        this.column = (num==0||num==37)?0:(num%3==0?3:num%3);
        this.color = setColor(num);
    }

    /**
     * Determines the color of the number based on American roulette.
     * The color of the number: 0 for green, 1 for red, and 2 for black
     *
     * @param num the number to set the color for
     * @return the color code of the number
     */

    public char setColor(int num){
        int[] RED_NUMBERS = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
        if (num<37 && num >0){
            if(Arrays.binarySearch(RED_NUMBERS,num)>=0)
                return 1;
            else
                return 2;
        }
        else
            return 0;
    }
    /**
     * Returns the string of the number.
     *
     * @return the string of the number
     */
public String getNumString(){return numString;}
    /**
     * Returns the actual value of the number
     *
     * @return the number
     */
public int getNum(){
    return num;
}
    /**
     * Returns the color of the number
     *
     * @return the color
     */
public int getColor(){
    return color;
}
    /**
     * Returns the row of the number
     *
     * @return the number's row
     */
public int getRow(){
    return row;
}
    /**
     * Returns the column of the number
     *
     * @return the number's column
     */
public int getColumn(){
    return column;
}
}