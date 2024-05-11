import java.util.Arrays;


//Holds number statistics.
public class RouletteNum {
    private final int num;
    private final int color;
    private final int row;
    private final int column;
    String numString;


    public RouletteNum(int num){
        this.num = num;
        this.numString = (num == 37 ? "00" : Integer.toString(num));
        this.row = ((num==0||num==37)?0:(int)Math.ceil(num/3.0));
        this.column = (num==0||num==37)?0:(num%3==0?3:num%3);
        this.color = setColor(num);
    }
//set number color to 0(green), 1(red) or 2(black), based off american roulette table.
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

public String getNumString(){return numString;}
public int getNum(){
    return num;
}
public int getColor(){
    return color;
}
public int getRow(){
    return row;
}
public int getColumn(){
    return column;
}
}