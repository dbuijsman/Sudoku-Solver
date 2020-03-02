import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

class Puzzle{
    // 2D array of rows, columns and blocks.
    private Line[][] lines = new Line[3][9];
    private Stack<Tile[][]> stack = new Stack<>();
    Puzzle(String argument){
        if(argument.length()!=81){
            System.out.println("Input must have length 81.");
            throw new IllegalArgumentException("Input must have length 81");
        }
        for(int column=0; column<9; column++){
            this.lines[0][column] = new Line(argument.substring(9*column,9*column+9));
        }
        for(int index=0;index<9;index++){
            ArrayList<Tile> tiles = new ArrayList<>();
            for(Line row:this.lines[0]){
                tiles.add(row.getTile(index));
            }

            this.lines[1][index] = new Line(tiles.toArray(new Tile[9]));
        }
        for(int index=0;index<9;index++){
            ArrayList<Tile> tiles = new ArrayList<>();
            for(int subIndex=0;subIndex<9;subIndex++){
                tiles.add(this.lines[0][3*(index/3)+subIndex/3].getTile(3*(index%3)+subIndex%3));
            }
            this.lines[2][index] = new Line(tiles.toArray(new Tile[9]));
        }
    }
    boolean removePossibleEntries(){
        boolean changedTile = false;
        boolean changedInLoop;
        do {
            changedInLoop = false;
            for (Line[] lines : this.lines) {
                for (Line line : lines) {
                    if (line.removePossibleEntries()) {
                        changedTile = true;
                        changedInLoop = true;
                    }
                }
            }
        }while(changedInLoop);
        return changedTile;
    }

    private void fillInCertainties(){
        boolean changedTile;
        removePossibleEntries();
        do{
            changedTile = false;
            for(Line[] lines:this.lines){
                for(Line line:lines){
                    if(line.fillInUniqueSpotNumber() | removePossibleEntries()){
                        changedTile = true;
                    }
                    if(!isValid()){
                        return;
                    }
                }
            }
        }while(changedTile);
    }
    private void copyTiles(){
        Tile[][] tiles = new Tile[9][9];
        for(int row=0;row<9;row++){
            for(int column=0;column<9;column++){
                tiles[row][column] = new Tile(this.lines[0][row].getTile(column));
            }
        }
        this.stack.push(tiles);
    }
    void makeGuess(){
        copyTiles();
        for(Line row: this.lines[0]){
            if(!row.isFilledIn()){
                row.makeGuess();
                return;
            }
        }
    }
    void undoLastGuess(){
        Tile[][] oldTiles = stack.pop();
        for(int row=0;row<9;row++){
            lines[0][row].setTiles(oldTiles[row]);
        }
        for(Line row: this.lines[0]){
            if(!row.isFilledIn()){
                row.removeGuess();
                return;
            }
        }
    }

    boolean isValid(){
        for(Line[] lines:this.lines){
            for(Line line:lines){
                if(!line.isValid()){
                    return false;
                }
            }
        }
        return true;
    }
    boolean isSolved(){
        for(Line[] lines:this.lines){
            for(Line line:lines){
                if(!line.isFilledIn()){
                    return false;
                }
            }
        }
        return true;
    }
    public void solve(){
        fillInCertainties();
        System.out.println("Got");
        print();
        while(!isSolved() && isValid()){
            try {
                makeGuess();
                fillInCertainties();
                if (!isValid()) {
                    undoLastGuess();
                }
            }
            catch(EmptyStackException emptyStack){
                break;
            }
        }
        if(!isValid()){
            System.out.println("This sudoku is not solvable!");
        } else if(isSolved()){
            System.out.println("Solved!");
        }
    }
    public void print(){
        for(Line row : this.lines[0]) {
            System.out.println("-------------------");
            System.out.println((row.getValues().toString()).replace("0", " ").replace("[", "|").replace(", ", "|").replace("]", "|"));
        }

    }

}