package cs61bxl;

import java.lang.*;

/**
 * This class is an identifier of a board state. It can recreate a board
 * based on its whiteId and blackId values. 
 * Also, it will be used for hashing board states. It is more memory 
 * efficent that keeping individual boards for each reference in the ht.
 **/
public class BoardId{
    long whiteId; //Binary representation of the location of white pieces.
                  //1 if square has a white, 0 other wise.
    long blackId; //Binary representation of the location of black pieces. 
                  //1 if square has a black, 0 otherwise. 
                  //The square coords are the location of the binary bit. 
                  //eg, for square coord (2, 9) look at 29th bit.

    /**
     * Constructor for boardId. Takes in a board, and updates the 
     * values for blackId and whiteId accordinly.
     * @param board the board for whose id is wanted.
     **/
    public BoardId(Board board){
        whiteId = 0L;
        blackId = 0L;
        long powOf2 = 1L;
        Square[][] b = board.board;
        for(int x = 0; x < b.length; x++){
            for(int y = 0; y < b[0].length; y++){
                if(b[y][x].color == Square.WHITE){
                    whiteId += powOf2;
                } else if (b[y][x].color == Square.BLACK) {
                    blackId += powOf2;
                }
                powOf2 = powOf2 * 2;
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
        return ((Long) whiteId).hashCode() ^ ((Long) blackId).hashCode();
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
            BoardId otherBoardId = (BoardId) other ;
            return (whiteId == otherBoardId.whiteId
                    && blackId == otherBoardId.blackId);
        } else {
            return false;
        }
    }
}
