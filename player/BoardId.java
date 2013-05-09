package player;

import java.lang.*;

/**
 * This class is an identifier of a board state. It can recreate a board
 * based on its whiteId and blackId values. 
 * Also, it will be used for hashing board states. It is more memory 
 * efficent that keeping individual boards for each reference in the ht.
 **/
public class BoardId{
    String id; //String compression of the board;

    /**
     * Constructor for boardId. Takes in a board, and updates the 
     * value of id appropriately.
     * @param board the board for whose id is wanted.
     */

    public BoardId(Board board){
        Square[][] b = board.board;
        id = "";
        for(int x = 0; x < b.length; x++){
            for(int y = 0; y < b[0].length; y++){
                if(b[y][x].color == Square.WHITE){
                    id += "2";
                } else if (b[y][x].color == Square.BLACK){
                    id += "1";
                } else {
                    id += "0";
                }
            }
        }
    }

    /**
     * Returns a hash code based on the white Id and black Id values
     * uses XOR to create a balanced hashcode. 
     * Formula (whiteId.hashCode() ^ blackId.hashCode());
     * @returns hashCode for board Id.
     **/
    public int hashCode(){
        return id.hashCode();
    }

    /**
     * @Overrides object.equals. 
     * Checks to see if whiteID and blackId of the other Board has same 
     * values. if other is not a board id, returns false;
     * @param other the other object that checks equality.
     * @return true if other is a board id and has same instance fields
     * false otherwise
     **/
    public boolean equals(Object other){
        if(other instanceof BoardId){ 
            BoardId otherBoardId = (BoardId) other;
            return otherBoardId.id.equals(this.id);
        } else {
            return false;
        }
    }
}
