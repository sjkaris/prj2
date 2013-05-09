package cs61bxy;
/* Board.java */


//Notes//
//We may want a variable to hold how many chips are on the board
//A variable saying where exactly the chips are adjacent to a square would be helpful too

import cs61bxl.list.*;
import cs61bxy.tree.*;
import cs61bxl.dict.*;
import player.Move;

public class Board {
	
	
	
	/* These are Board's variables */
	protected Square[][] board;
	//whites and blacks track how many white and black pieces are on the board
	protected int whites;
	protected int blacks;



	/**
	* creates an empty board with each Square set to empty
	**/
	public Board(){
		board=new Square[8][8];
		whites=0;
		blacks=0;
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				board[j][i]=new Square(i,j);
			}
		}
	}

    /**
     * Constructor that creates a copy of old board
     **/
    public Board(Board oldBoard){
        this.board = new Square[8][8];
        this.whites = oldBoard.whites;
        this.blacks = oldBoard.blacks;
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                this.board[y][x] = new Square(x, y, oldBoard.board[y][x].color);
            }
        }
        this.updateBoard(new Move(7,7), Square.EMPTY);
    }

	
	
	
    
    /**
    *	checks if moves is valid
    *	@param Move takes in move m as the move to check
    *	@param color takes in the color of the piece
    *	returns true if move is valid, and false if otherwise. 
    **/
    public boolean checkMove(Move m, int color){
        if (m.moveKind == Move.QUIT){
            return true;
        }
        else if(m.moveKind == Move.ADD){
            if (!onBoard(m.x1, m.y1, color)) {return false;}
            else if(board[m.y1][m.x1].getColor() != Square.EMPTY){return false;}
            else if(!board[m.y1][m.x1].validForAdj(color, m)){ return false;}
            else if(color == Square.BLACK && blacks >= 10) {return false;}
            else if(color == Square.WHITE && whites >= 10){return false;}
        }
        else if(m.moveKind == Move.STEP){
            if (color == Square.BLACK && blacks < 10){return false;}
            else if(m.x1 == m.x2 && m.y1 == m.y2){return false;}
            else if(color == Square.WHITE && whites < 10){return false;}
            else if(!onBoard(m.x1, m.y1, color)){return false;}
            else if(board[m.y1][m.x1].color != Square.EMPTY){return false;}
            else if(board[m.y2][m.x2].color != color){return false;}
            updateBoard(m, color);
            if(!board[m.y1][m.x1].validForAdj(color, m)){ 
                undoMove(m, color); 
                return false;
            }
            undoMove(m, color);
        }
        return true;
    }
    /**
    *	checks if the piece being placed is on the playable board for its color, if the board was empty.
    *	@param x takes in the x coordinate of the desired placement.
    *	@param y takes in the y coordinate of the desired placement.
    *	@param color takes in the color of the piece.
    *	returns true if a piece of that color is in a playable location and false if otherwise.
    **/
    private boolean onBoard(int x, int y, int color){
        if(0<=x && x<8 && 0<=y && y<8){
            if(x == 0 && y == 0){
                return false;
            }
            else if(x == 0 && y == 7){
                return false;
            }
            else if(x == 7 && y == 0){
                return false;
            }
            else if(x == 7 && y == 7){
                return false;
            }
            else if(color == Square.BLACK ){
                if(x == 0 || x == 7){ return false;}
            }
            else if(color == Square.WHITE){
                if(y == 0 || y == 7) {return false;}
            }
            return true;
        } else { 
            return false;
        }
    }

    
    
    
    /**
     *	Checks if a player has won. If they have not won, then a tree consisting of all paths from
     *	one side to the other is built and returned.
     *	@param color the color of the network.
     *	@return returns a tree consisting of all possible paths from one goal to the next goal. Tree 
     *	ends building if it has found a network.
     **/
    public ConnectTree checkNetwork(int color){
        Square[][] starts = getStart(color);
        Square[] start;
        HashTableChained visited,
                         end;

        int counter1 = 0,
            counter2 = 0;
        for(int i = 0; i < starts[0].length; i++){
            if(starts[0][i] != null){
                counter1++;
            } 
            if(starts[1][i] != null){
                counter2++;
            }
        }

        if(counter1 > counter2){
            end = getGoalArea2(color);
            visited = getGoalArea1(color);
            start = starts[0];
        } else {
            end = getGoalArea1(color);
            visited = getGoalArea2(color);
            start = starts[1];
        }

        Stack prevVisited  = new Stack(); //Holds squares
        ConnectTree tree = new ConnectTree(color);
        tree.getRoot().addArrayOfChildren(start, true);
        ConnectTreeNode currNode;
        if(tree.getRoot().hasChild()){
            currNode = tree.getRoot().getFirstChild();
        } else{
            return tree;
        }
        while(currNode != tree.getRoot()){ 
            if(end.find(currNode.getSquare()) != null){
                if(currNode.getDepth() >= 6){
                    tree.setWinner(true);
                    return tree;
                } else {
                    currNode = nextNode(currNode, tree, visited, prevVisited); 
                }
            } else {
                currNode.addConnections(currNode.getSquare().connections, visited);
                currNode = nextNode(currNode, tree, visited, prevVisited); 
            }
        }
        return tree;
    }
    
   

    /**
     * Helper method that modifies currNode to the next node that must be developed
     * in the depth first search.
     *
     * If the node has children, currNode becomes the first child;
     *
     * If the node doesnt have children, it becomes its parents sibling. If that
     * parent doesnt have siblings, it walks up the tree until one of its
     * grandpappys have a sibling.
     *
     * If the node doesnt have children nor it (grand^x)parents have siblings,
     *      then the node becomes the root node.
     *
     * Everytime the function walks up the tree, it removes a entry from the
     *      stack, and removes the corresponding entry in visited.
     *
     * Everytime the function walks down the tree, it adds the previous node's
     *      square onto the stack and hashtable
     *
     * Ideally, this function is to be called after addConnections or th
     * e tree has hit a path with a node in the goal zone.
     *
     * @param currNode, the node that is being modified.
     * @param tree the tree that currNode belongs to.
     * @param visited A hash table that contains the nodes that the path has already
     *          visited.
     * @param prevVisited a stack that contains a list of previous nodes that we have
     *      visited;
     **/
    private ConnectTreeNode nextNode(ConnectTreeNode currNode, ConnectTree tree,
            HashTableChained visited, Stack prevVisited){
        if(currNode.hasChild()){
            visited.insert(currNode.getSquare(), (Boolean) true);
            prevVisited.push(currNode.getSquare());
            currNode = currNode.getFirstChild();
        } else if (currNode.hasSibling()) {
            currNode = currNode.getSibling();
        } else {
            do{
                currNode = currNode.getParent();
                Square holder = (Square) prevVisited.pop();
                if(holder != null){
                    visited.remove(holder);
                }
            } while(!currNode.hasSibling() && currNode != tree.getRoot());
            
            if(currNode != tree.getRoot()){
                currNode = currNode.getSibling();
            }
        }

        return currNode;
    }

    /**
     * Helper method that returns a hashtable of elements of valid squares in goal area;
     * If the color is black, returns top row. If white, returns left col.
     *
     * @param color of goal area 
     * @return hashtable that contains squares that have a chip in them. 
     **/
    private HashTableChained getGoalArea1(int color){
        HashTableChained dict = new HashTableChained(20);
        if(color == Square.BLACK){
            int y = 0;
            for(int x = 1; x < 7; x++){
                if(board[y][x].color == Square.BLACK){
                    dict.insert(board[y][x], (Boolean) true);
                }
            }
        } else {
            int x = 0;
            for(int y = 1; y < 7; y++){
                if(board[y][x].color == Square.WHITE){
                    dict.insert(board[y][x], (Boolean) true);
                }
            }
        }

        return dict;
    }

    /**
     * Helper method that returns a hashtable of elements of valid squares in goal area;
     * If the color is black, returns bottom row. If white, returns right col.
     *
     * @param color of goal area 
     * @return hashtable that contains squares that have a chip in them. 
     **/
    private HashTableChained getGoalArea2(int color){
        HashTableChained dict = new HashTableChained(20);
        if(color == Square.BLACK){
            int y = 7;
            for(int x = 1; x < 7; x++){
                if(board[y][x].color == Square.BLACK){
                    dict.insert(board[y][x], (Boolean) true);
                }
            }
        } else {
            int x = 7;
            for(int y = 1; y < 7; y++){
                if(board[y][x].color == Square.WHITE){
                    dict.insert(board[y][x], (Boolean) true);
                }
            }
        }
        return dict;
    }
    
    /**
     * Helper method that returns the array of the starting part.
     * It can contain squares that do not have pieces.
     * @param color the color of the starting area.
     * @return array of squares of corresponding color row or column. If color = black, 
     * @return returns top row. Else returns left column.
     **/
    private Square[][] getStart(int color){
        Square[] arr = new Square[8];
        Square[] otherArr = new Square[8];
        if(color == Square.BLACK){
            for(int i = 0; i < arr.length; i++){
                if(board[0][i].color == Square.BLACK){
                    arr[i] = board[0][i];
                }
                if(board[7][i].color == Square.BLACK){
                    otherArr[i] = board[7][i];
                }
            }
        } else {
            for(int i = 0; i < arr.length; i++){
                if(board[i][0].color == Square.WHITE){
                    arr[i] = board[i][0];
                }
                if(board[i][7].color == Square.WHITE){
                    otherArr[i] = board[i][7];
                }
            }
        }
        Square[][] matrix = new Square[2][8];
        matrix[0] = arr;
        matrix[1] = otherArr;
        return matrix;
    }

    
    /** 
    *	This method updates the board after a move is made
    *	@param move, the move that will be added to the board
    *	@param color, the color of the player that is making the move
    **/
    public void updateBoard(Move m, int color){
        if(color == Square.BLACK && m.moveKind == Move.ADD){
            blacks++;
            board[m.y1][m.x1].color = color;
        }
        else if(color==Square.WHITE && m.moveKind == Move.ADD){
            whites++;
            board[m.y1][m.x1].color = color;
        }
        else if(m.moveKind == Move.STEP){
            board[m.y2][m.x2].color = Square.EMPTY;
            board[m.y1][m.x1].color = color;
        }
        for(int x=0; x<8; x++){
            for(int y=0; y<8; y++){
                board[y][x].updateAdj(this);
                board[y][x].updateConnections(this);
            }
        }
    }
    
    
    /**
    *	undoes a move that has been done. 
    *	@param color c is the color of the move being undone.
    *	@param move m is the move that was made.
    **/
    public void undoMove(Move m, int c){
        if(m.moveKind == Move.ADD){
            board[m.y1][m.x1].color = Square.EMPTY;
            if(c == Square.BLACK){
                blacks--;
            } else if (c== Square.WHITE){
                whites--;
            }
            updateBoard(new Move(7,7), Square.EMPTY);
        } else if (m.moveKind == Move.STEP) {
            updateBoard(new Move(m.x2, m.y2, m.x1, m.y1), c);
        }
    }
    
    /**
    *	Returns a list of valid moves for a color for current 
    *	@param color is the piece being identified
    *	@return returns a list of Moves that are valid for current board
    **/
    public List validMoves(int color){
        List valids= new SList();
        //This checks where we can do add moves
        if((color == Square.WHITE && whites < 10) || (color == Square.BLACK && blacks<10)){
            for(int x=0; x<8; x++){
                for(int y=0; y<8; y++){
                    Move m = new Move(x,y);
                    if(checkMove(m , color)){
                        valids.insertBack(m);
                    }
                }
            }
        }
        //This checks where we can do step moves
        else if((color== Square.WHITE && whites>=10) || (color == Square.BLACK && blacks >= 10)){
            for(int x=0; x<8; x++){
                for(int y=0; y<8; y++){
                    if(board[y][x].color == color){
                        for(int i = 0; i <= 7; i++){
                            for(int j = 0; j <= 7; j++){
                                Move m = new Move(i, j, x, y);
                                if(checkMove(m,color)){
                                    valids.insertBack(m);
                                }
                            }
                        }
                    }
                }
            }
        }
        return valids;
    }
    


    /**
    * Returns the probability of a board winning
    * @param color is the color that is being evalutated on the board
    **/
    public double evalBoard(int c){

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                board[j][i].updateConnections(this);
            }
        } 
        int otherC = (c+1) % 2;
        ConnectTree cTree = checkNetwork(c);
        ConnectTree otherCTree = checkNetwork(otherC); 
        
        if(otherCTree.hasNetwork()){
            return -1;
        }
        else if (cTree.hasNetwork()){
            return 1;
        }
        else{
	        double scoreLength = 2*(cTree.longestPath() * cTree.longestPath()) - otherCTree.longestPath() * otherCTree.longestPath();
	        double scoreNode;
	        if(cTree.getNumOfNodes() + otherCTree.getNumOfNodes() != 0){
	          	scoreNode = ((1.3*cTree.getNumOfNodes()) - otherCTree.getNumOfNodes())/((cTree.getNumOfNodes()*1.3) + otherCTree.getNumOfNodes());
	        }
	        else{
	          	scoreNode = 0; 
	        }
            double score = (scoreLength / 200)*.7 + (scoreNode)*.3;
            if(score > 1){
                System.out.println("POOOP");
            }
            return score;
        }
    }
    
	    
	    
		/**
		* 	gets the color of the square at position (x,y)
		*	@param x: the x-coordinate of the desired square.
		*	@param y: the y-coordinate of the desired square. 
		*	returns the color (-1 for empty, 0 for white, 1 for black) of the square at position
		*	(x,y). If (x,y) is not on the board, return -1. 
		**/
	public int getColor(int x, int y){
		if(0 <= x && x<= 7 && 0 <= y && y <= 7){
			return board[y][x].color;
		}
		else{
			return Square.EMPTY;
		}
	}

	/**
	 *	gives a pictoral representation of the current board with 0 as white
	 *	and X as black. 
	**/
    public String toString(){
        String txt = "  ";
        for(int i = 0; i < 8; i++){
           txt += i + " ";
        }

        txt += "\n";

        for(int y = 0; y < board.length; y++){
            txt += ("" + y + " ");
            for(int x = 0; x < board[0].length; x++){
                txt += (board[y][x] + " ");
            }
            txt += "\n"; 
        }
        return txt;
    }
    /**
    *returns the square at (x,y)
    * @param int x: is the x coordinate of the desired square.
    * @param int y: is the y coordinate of the desired square.
    * returns the square at (x,y) 
    **/
    public Square getSquare(int x, int y){
        return board[y][x];
    }
    /*
	*returns a hashCode for the board.
    */
    public int hashCode(){
    	int hashVal = 0;
	    for(int i = 0; i < 8; i++){
	      for(int j = 0; j < 8; j++){
	        hashVal = (3*hashVal + (this.getColor(i,j) + 1))%16908799;
	      }
	    }
	    return hashVal;
    }
}
