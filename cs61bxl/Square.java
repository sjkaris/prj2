package cs61bxl;

/**
*  Represents a square on the Netowrk game board
*	a black piece is represented by 1, white 0, and empty -1.
*	@method updateConnections() -> updates connections from this square.
*	Invariants: 
*	- The connections variable is always ed clockwise 
*		from the top connection. i.e. [top, top-right, right, ... , top-left].
*	- if a connection does not exist, the connection is listed as null.
*	- all connections are stored as their square object.
**/
import player.Move;

public class Square{

    public final static int BLACK = 1;
	public final static int WHITE = 0;
	public final static int EMPTY = -1;
	protected int color;
	protected int pX;
	protected int pY;
	protected int adjWhitePieces;
	protected int adjBlackPieces;
	protected Square[] adjWhite = new Square[8];
	protected Square[] adjBlack = new Square[8];
	protected Square[] connections = new Square[8];

    /**
     *  Abstraction taht represents directions used in connections.
     *  I dont know if this will be useful. Just including it for now.
     *      -Sumeet
     **/
    public static enum Direction{
        TOP, TOPR, R, BOTR, BOT, BOTL, L, TOPL;
    }

	/**
    *    Creates a square with in the position position with the color color
    *    @param position is a number between 00 and 76 inclusive. color is one of the
    *    above constants
    **/
	public Square(int x, int y, int c){
		color = c;
		pX = x;
		pY = y;
	}

	/**
    *    Creates a square with in the position position with no color, ie empty
    *    @param x is a number between 0 and 7 inclusive.
    *     @param y is a number between 0 and 7 inclusive.
    **/
	public Square(int x, int y){
		color = EMPTY;
		pX = x;
		pY = y;
	}

	/**
	*    This updates the connections that the given Square has.
	*    @param takes in the Board b that need was added to the board
	**/
	public void updateConnections(Board b){
		if(this.color != EMPTY){
			int x = pX;
			int y = pY;
			int connecting = 0;
			while(connecting < 8){
				do{
					if(connecting == 0)     {      y--;}
					else if(connecting == 1){ x++; y--;}
					else if(connecting == 2){ x++;     }
					else if(connecting == 3){ x++; y++;}
					else if(connecting == 4){ 	   y++;}
					else if(connecting == 5){ x--; y--;}
					else if(connecting == 6){ x--;     }
					else if(connecting == 7){ x--; y++;}
				}while(b.getColor(x,y) == EMPTY && x > 0 && x < 7 &&
	                    y > 0 && y < 7);

				if(b.getColor(x,y) == color){
					connections[connecting] = b.board[y][x];
				}
				else{
					connections[connecting] =  null;
				}
	            connecting++;
				x = pX;
				y = pY;
			}
		}
	}

	/**
	*    gets the color of the square.
	*    returns the integer value (-1 for empty, 0 for white, 1 for black) of the Square.
	**/
	public int getColor(){
		return this.color;
	}

	/**
	*    Counts the number of adjacent pieces of color color and updates the instance  
	*    vars adjWhitePieces and adjBlackPieces
	*    @param color is the color of adjacent pieces being counted.
	*    @param Board b is the board on which adjacent pieces are being checked
	**/
	public void updateAdj(Board b){
		adjWhitePieces = 0;
		adjBlackPieces = 0;
		adjWhite = new Square[8];
		adjBlack = new Square[8];
		int counter = 0;
		for(int i = pX-1; i <= pX+1; i++){
			for(int j = pY-1; j <= pY+1; j++){
				if(!(i == pX && j == pY)){
					if(b.getColor(i,j) == WHITE){
						adjWhitePieces++;
						adjWhite[counter] = b.board[j][i];
						counter++;
					}
					else if(b.getColor(i,j) == BLACK){
						adjBlackPieces++;
						adjBlack[counter] = b.board[j][i];
						counter++;
					}
				}
			}
		}
	}
    
    /**
	*    returns if a square is valid for color c with the adj rule.  
	*    @param color c is the color of the piece to be added.
	**/
	public boolean validForAdj(int c, Move m){
		int moveType = 0;
		if(m.moveKind == Move.ADD){
			moveType = 0;
		}
		else if(m.moveKind == Move.STEP){
			moveType = 1;
		}
		if(c == WHITE){
			if (adjWhitePieces >= 2){ return false;}
			else{
				for(Square i : adjWhite){
					if(i != null && i.adjWhitePieces > moveType){
						return false;
					}
				}
			}
		}
		else if(c == BLACK){
			if (adjBlackPieces >= 2){ return false;}
			else{
				for(Square i : adjBlack){
					if(i != null && i.adjBlackPieces > moveType){
						return false;
					}
				}
			}
		}
		return true;
	}
    
    /**
	*    returns if a square is valid for color c with the adj rule for step moves
	*    @param color c is the color of the piece to be added.
	**/
	public boolean validForAdjStep(int c){
		if(c == WHITE){
			if (adjWhitePieces >= 3){
                return false;
            }
		}
		else if(c == BLACK){
			if (adjBlackPieces >= 3){
                return false;
            }
		}
		return true;
	}

    /**
     * hashcode for squares. Uses the unique id of the squares location
     * @return the coordinate as a two digit number.
     **/
    public int hashCode(){
        return pX * 10 + pY;
    }

    /**
     * turns square into string.
     * @return if square is black, then X,
     *         else if white, then O
     *         else -
     **/
    public String toString(){
        if(color == Square.BLACK){
            return "X";
        } else if (color == Square.WHITE) {
            return "O";
        } else {
            return "-";
        }
    }
    /**
	 *	returns the connections of this square.
    **/
    public Square[] getConnections(){
        return connections;
    }
    /**
	 *	prints the coordinates of this square.
    **/
    public void printCoords(){
        System.out.print(pX + "," + pY);
        System.out.println();
    }
    /**
	 *	returns a string representation of (x,y) of this square.
    **/
    public String getCoords(){
    	return "" + (pX*10 + pY);
    }



}
