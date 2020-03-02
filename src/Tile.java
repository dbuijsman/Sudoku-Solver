import java.util.ArrayList;
import java.util.Arrays;

public class Tile {
    private ArrayList<Integer> possibleEntries = new ArrayList();
    private int value;
    Tile(int value){
        this.value = value;
        if(value==0){
            this.possibleEntries = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        }
    }
    Tile(Tile oldTile){
        this.value = oldTile.getValue();
        this.possibleEntries = new ArrayList<>(oldTile.getPossibleEntries());
    }
    void setTile(Tile oldTile){
        this.value = oldTile.getValue();
        this.possibleEntries = new ArrayList<>(oldTile.getPossibleEntries());
    }
    int getValue(){
        return this.value;
    }
    void setValue(int value){
        if(this.possibleEntries.contains(value)){
            this.value=value;
            this.possibleEntries.clear();
        }
    }
    ArrayList<Integer> getPossibleEntries(){
        return this.possibleEntries;
    }
    boolean removePossibleValue(Integer entry){
        boolean changedPossibleEntries = this.possibleEntries.remove(entry);
        if(possibleEntries.size()==1){
            this.value = possibleEntries.get(0);
            this.possibleEntries.clear();
        }
        return changedPossibleEntries;
    }
    void makeGuess(){
        this.value = this.possibleEntries.get(0);
        this.possibleEntries.clear();
    }
    void removeGuess(){
        removePossibleValue(this.possibleEntries.get(0));
    }
}
