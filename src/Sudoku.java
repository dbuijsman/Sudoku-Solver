import java.util.Scanner;
public class Sudoku {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        String sudokuAsString = (args != null && args.length>0) ? args[0] : "";
        Puzzle puzzle = null;
        while(sudokuAsString.length() != 81){
            System.out.println("Please enter a string that represents the sudoku. Fill in 0 for empty tiles:");
            input.next();
            sudokuAsString = input.nextLine();
            try{
                puzzle = new Puzzle(sudokuAsString);
            } catch(IllegalArgumentException notASudoku){
                sudokuAsString = "";
            }
        }
        System.out.println("Initial state");
        puzzle.print();
        puzzle.solve();
        System.out.println("Final state");
        puzzle.print();
    }
}
