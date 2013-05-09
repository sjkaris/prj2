/* MachinePlayer.java */
package cs61bxl;


import cs61bxl.list.*;
import cs61bxl.dict.*;
import cs61bxl.tree.*;
import player.Player;
import player.Move;

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {

    Board theBoard;
    int color;
    int depth;
    String myName;

  // Creates a machine player with the given color.  Color is either 0 (black)
  // or 1 (white).  (White has the first move.)
    public MachinePlayer(int color) {
        if(color == 1){
            this.color = Square.WHITE;
        } else {
            this.color = Square.BLACK;
        }
        depth = 4; //Will change;
        theBoard = new Board();
        myName = "Machine";
    }

    // Creates a machine player with the given color and search depth.  Color is
    // either 0 (black) or 1 (white).  (White has the first move.)
    public MachinePlayer(int color, int searchDepth) {
        this(color);
        depth = searchDepth;
    }

    // Returns a new move by "this" player.  Internally records the move (updates
    // the internal game board) as a move by "this" player.
    public Move chooseMove() {
        long start = System.currentTimeMillis();
        Best temp;
        if(theBoard.whites + theBoard.blacks == 20){
            temp = chooseMove(color, -100000, 100000, new HashTableChained(30000), depth - 1);
        } else { 
            temp = chooseMove(color,-10000,100000, new HashTableChained(10000), depth);
        }
        if(temp.move.moveKind == Move.QUIT){
            try{
                System.out.println("WE LOSE");
                List validMoves = theBoard.validMoves(color);
                temp.move = (Move) validMoves.front().item();
            }
            catch(InvalidNodeException e){}
        }
        System.out.println("Score: " + temp.score);
        forceMove(temp.move);
        Long end = System.currentTimeMillis();
        System.out.println("time: " + (end - start));
        return temp.move;
    } 
    /**
     *  returns a best containing a move and a score with alpha-beta pruning with a depth of depth.
     *  @param int player is the current player.
     *  @param double alpha is the max score of the boards so far between -1 and 1.
     *  @param double beta is the min score of the boards so far between -1 and 1.
     *  @param prevBoards is a hashTable of the boards visited and evaluated so far
     *  @param int depth is the depth of the game tree that will be searched. 
     *  returns a best containing the best move and its score.
    **/
    public Best chooseMove(int player, double alpha, double beta,
            HashTableChained prevBoards, int depth){
        try {
            Best myBest = new Best();
            Best reply;
            int otherPlayer = (player + 1) % 2;
            double eval;
            BoardId currBoard = new BoardId(theBoard);
            if(prevBoards.find(currBoard) != null){
                eval = ((Double) prevBoards.find(currBoard).value());
            }
            else{
                eval = theBoard.evalBoard(color);
                if( Math.abs(Math.abs(eval) - 1) < .01 || Math.abs(eval) > 1){
                    eval = eval * (depth+1);
                }
            }
            //If winner
            if( Math.abs(Math.abs(eval) - 1) < .01 || Math.abs(eval) > 1){
                return new Best(eval);
            }

            if(player == this.color){
                myBest.score = alpha;
            } else {
                myBest.score = beta;
            }

            List validMoves = theBoard.validMoves(player);

            while(!validMoves.isEmpty()){
                //Get move, put it on, get reply, take it off
                Move move = (Move) validMoves.front().item();
                validMoves.front().remove();
                //Board oldBoardHolder = new Board(theBoard);
                theBoard.updateBoard(move, player);
                BoardId newBoard = new BoardId(theBoard);
                double replyScore;

                if(prevBoards.find(newBoard) != null){
                    replyScore = ((Double)prevBoards.find(newBoard).value());
                    reply = new Best(replyScore, move);
                } 
                else if(depth > 1){
                    reply = chooseMove(otherPlayer, alpha, beta, prevBoards, depth - 1);
                    prevBoards.insert(newBoard, reply.score);
                    
                } else {
                    replyScore = theBoard.evalBoard(color);
                    reply = new Best(replyScore, move);
                    prevBoards.insert(newBoard, replyScore);
                }

                //theBoard = oldBoardHolder;
                theBoard.undoMove(move, player);

                if((player == this.color) && (reply.score > myBest.score)){
                    myBest.move = move;
                    myBest.score = reply.score;
                    alpha = reply.score;
                } else if (player == (this.color + 1) % 2 && reply.score < myBest.score) {
                    myBest.move = move;
                    myBest.score = reply.score;
                    beta = reply.score;
                }
                if (alpha >= beta){
                    return myBest;
                }
            }

            return myBest;
        } catch (InvalidNodeException e){
            System.err.println(e);
            return null;
        }
    } 


  // If the Move m is legal, records the move as a move by the opponent
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method allows your opponents to inform you of their moves.
    public boolean opponentMove(Move m) {
        if(theBoard.checkMove(m, (this.color + 1) % 2)){
          theBoard.updateBoard(m, (this.color + 1) % 2);
          return true;
        } else {
          return false;
        }
    }

  // If the Move m is legal, records the move as a move by "this" player
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method is used to help set up "Network problems" for your
  // player to solve.
    public boolean forceMove(Move m) {
        if(theBoard.checkMove(m, this.color)){
          theBoard.updateBoard(m, this.color);
          return true;
        } else {
          return false;
        }
    }
    /**
     *  returns the internal board of this machinePlayer.
    **/
    public Board getBoard(){
        return this.theBoard;
    }
}
