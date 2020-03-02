import java.util.ArrayList;
import java.util.HashSet;

public class Line {
    private Tile[] tiles;
    Line(Tile[] tiles){
        this.tiles=tiles;
    }
    Line(String values){
        try{
            ArrayList<Tile> tiles = new ArrayList<>();
            for(int index=0;index<9;index++){
                Tile tile = new Tile(Integer.parseInt(values.substring(index,index+1)));
                tiles.add(tile);
            }
            this.tiles = tiles.toArray(new Tile[9]);
        }catch (NumberFormatException nfe){
            System.out.println("Only integers will be accepted.");
            throw new IllegalArgumentException("Can not parse the given input.");
        }
    }
    Tile getTile(int index){
        return this.tiles[index];
    }
    void setTiles(Tile[] newTiles){
        for(int index=0;index<9;index++){
            this.tiles[index].setTile(newTiles[index]);
        }
    }
    ArrayList<Integer> getValues(){
        ArrayList<Integer> values = new ArrayList<>();
        for(Tile tile:tiles){
            values.add(tile.getValue());
        }
        return values;
    }
    boolean removePossibleEntries(){
        ArrayList<Integer> certainValues = getValues();
        boolean changeOnLine = false;
        for(int value : certainValues){
            for(Tile tile:this.tiles){
                if(tile.removePossibleValue(value)){
                    changeOnLine=true;
                };
            }
        }
        return changeOnLine;
    }
    boolean fillInUniqueSpotNumber(){
        HashSet<Integer> missedNumbers = new HashSet<>();
        ArrayList<Integer> numbersWithUniqueSpot = new ArrayList<>();
        boolean isUnique;
        for(Tile tile:this.tiles){
            ArrayList<Integer> possibleEntries = tile.getPossibleEntries();
            for(int possibleEntry:possibleEntries){
                isUnique = missedNumbers.add(possibleEntry);
                if(isUnique){
                    numbersWithUniqueSpot.add(possibleEntry);
                } else {
                    numbersWithUniqueSpot.remove(Integer.valueOf(possibleEntry));
                }
            }
        }
        for(int number:numbersWithUniqueSpot){
            for(Tile tile:this.tiles){
                tile.setValue(number);
            }
        }
        return numbersWithUniqueSpot.size()>0;
    }
    void makeGuess(){
        if(isFilledIn()){
            throw new ArrayIndexOutOfBoundsException();
        }
        for(Tile tile:this.tiles){
            if(tile.getValue()==0){
                tile.makeGuess();
                return;
            }
        }
    }
    void removeGuess(){
        if(isFilledIn()){
            throw new ArrayIndexOutOfBoundsException();
        }
        for(Tile tile:this.tiles){
            if(tile.getValue()==0){
                tile.removeGuess();
                return;
            }
        }
    }
    boolean isValid(){
        ArrayList<Integer> values = getValues();
        while(values.remove(Integer.valueOf(0))){
            continue;
        }
        HashSet<Integer> uniqueValues = new HashSet<>(values);
        return values.size()==uniqueValues.size();
    }
    boolean isFilledIn(){
        ArrayList<Integer> values = getValues();
        while(values.remove(Integer.valueOf(0))){
            continue;
        }
        return values.size()==9;
    }
}
