package tests;

import player.*;
import tree.*;

public class testBoard{
	
	public static void p(Object o){
        System.out.println(o);
    }

    public static void main(String[] args){
        Board b = new Board();
        System.out.println("Printing Empty Board");
        System.out.println(b);

        System.out.println("Testing valid moves on an empty board. First with black, then white");
        System.out.println("The following should be 48: " + b.validMoves(Square.WHITE).length());
        System.out.println("The following should be 48: " + b.validMoves(Square.BLACK).length());

        //Other cases
        Board test=new Board();
		 System.out.println("add 5 black pieces, such that only one more piece at (5,0) will make a network");
		 Move addBlack1=new Move(3,3);
		 test.updateBoard(addBlack1,Square.BLACK);
		 Move addBlack2=new Move(5,3);
		 test.updateBoard(addBlack2,Square.BLACK);
		 Move addBlack3=new Move(2,5);
		 test.updateBoard(addBlack3,Square.BLACK);
		 Move addBlack4=new Move(5,5);
		 test.updateBoard(addBlack4,Square.BLACK);
		 Move addBlack5=new Move(2,7);
		 test.updateBoard(addBlack5,Square.BLACK);

         System.out.println(test);
		 
		 System.out.println("add 6 stupidly placed white pieces");
		 Move addWhite1=new Move(0,2);
		 test.updateBoard(addWhite1,Square.WHITE);
		 Move addWhite2=new Move(2,1);
		 test.updateBoard(addWhite2,Square.WHITE);
		 Move addWhite3=new Move(2,4);
		 test.updateBoard(addWhite3,Square.WHITE);
		 Move addWhite4=new Move(3,6);
		 test.updateBoard(addWhite4,Square.WHITE);
		 Move addWhite5=new Move(6,2);
		 test.updateBoard(addWhite5,Square.WHITE);
		 Move addWhite6=new Move(6,6);
		 test.updateBoard(addWhite6,Square.WHITE);

         System.out.println(test);

		
		 //TESTS FOR getColor//
         p("Tests for color");
		 //Should return 1
		 System.out.println("Should return 1: " + test.getColor(3, 3));
		//Should return 0
		 System.out.println("Should return 0: " +test.getColor(2,1));
		//Should return -1
		 System.out.println("Should return -1: " +test.getColor(0,0));
		//Should return -1
		 System.out.println("Should return -1: " +test.getColor(-3,0));
		//Should return -1
		 System.out.println("Should return -1: " +test.getColor(4,4));
		 
		//TESTS FOR checkMove//
		 p("");
		 p("******************************");
		 p("Tests for checkMove");

		 Move whereTo=new Move(5,0);
		 System.out.println("Should return true, since it's in the goal: "+test.checkMove(whereTo, Square.BLACK));
		 Move whereTo2=new Move(7,1);
		 System.out.println("Should return false, since it's in another goal: "+test.checkMove(whereTo2, Square.BLACK));
		 Move whereTo3=new Move(6,6);
		 System.out.println("Should return false, since there's already a piece there: "+test.checkMove(whereTo3, Square.BLACK));
		 Move whereTo4=new Move(4,3);
		 System.out.println("Should return false, since there's two adjacent pieces there: "+test.checkMove(whereTo4, Square.BLACK));
		 Move whereTo5=new Move(8,6);
		 System.out.println("Should return false, since the piece isn't on the board: "+test.checkMove(whereTo5, Square.BLACK));
		 
		 //More checkMove tests
		 //Add new black pieces
		 Board testGoals = new Board();
		 Move goalBlack1=new Move(3,0);
		 testGoals.updateBoard(goalBlack1,Square.BLACK);
		 Move goalBlack2=new Move(4,0);
		 testGoals.updateBoard(goalBlack2,Square.BLACK);
		 Move goalBlack3=new Move(1,7);
		 testGoals.updateBoard(goalBlack3,Square.BLACK);
		 Move goalBlack4=new Move(6,7);
		 testGoals.updateBoard(goalBlack4,Square.BLACK);
		 //Add new white pieces
		 Move goalWhite1=new Move(0,3);
		 testGoals.updateBoard(goalWhite1,Square.WHITE);
		 Move goalWhite2=new Move(0,5);
		 testGoals.updateBoard(goalWhite2,Square.WHITE);
		 Move goalWhite3=new Move(7,2);
		 testGoals.updateBoard(goalWhite3,Square.WHITE);
		 Move goalWhite4=new Move(7,4);
		 testGoals.updateBoard(goalWhite4,Square.WHITE);
		 
		 p("");
		 p("******************************");
		 p("More Tests for checkMove");
		 testGoals = new Board();
		 p(testGoals);
		 p("Trying to do a step move without all pieces used, should return false: " + testGoals.checkMove(new Move(1,1,3,3), Square.BLACK));
		 testGoals.updateBoard(new Move(3,2), Square.BLACK);
		 testGoals.updateBoard(new Move(3,3), Square.BLACK);
		 testGoals.updateBoard(new Move(5,6), Square.BLACK);
		 testGoals.updateBoard(new Move(6,5), Square.BLACK);
		 testGoals.updateBoard(new Move(1,1), Square.BLACK);
		 testGoals.updateBoard(new Move(1,4), Square.BLACK);
		 testGoals.updateBoard(new Move(1,7), Square.BLACK);
		 testGoals.updateBoard(new Move(6,4), Square.BLACK);
		 testGoals.updateBoard(new Move(6,0), Square.BLACK);
		 testGoals.updateBoard(new Move(3,7), Square.BLACK);
		 p(testGoals);
		 p("Trying illegal stepMove (moving to too many adj): " + testGoals.checkMove(new Move(3,4,1,1), Square.BLACK));
		 p("Trying illegal stepMove (no piece in orig loc): " + testGoals.checkMove(new Move(1,2,3,5), Square.BLACK));
		 p("Trying legal stepMove: " + testGoals.checkMove(new Move(1,2,1,1), Square.BLACK));
		 p("doing stepMove on above Board: ");
		 testGoals.updateBoard(new Move(1,2,1,1), Square.BLACK);
		 p(testGoals);
		 
		 p("");
		 p("******************************");
		 p("More Tests for checkNetwork");
		 Board testNetwork = new Board();
		 testNetwork.updateBoard(new Move(0, 2), Square.WHITE);
		 testNetwork.updateBoard(new Move(2, 2), Square.WHITE);
		 testNetwork.updateBoard(new Move(2, 3), Square.WHITE);
		 testNetwork.updateBoard(new Move(4, 5), Square.WHITE);
		 testNetwork.updateBoard(new Move(6, 5), Square.WHITE);
		 testNetwork.updateBoard(new Move(7, 6), Square.WHITE);
		 p(testNetwork);
		 p("testing checkNetwork.");
		 p("Should return true. White has a network: " + testNetwork.checkNetwork(Square.WHITE).hasNetwork());
		 testNetwork.updateBoard(new Move(6, 5), Square.BLACK);
		 p(testNetwork);
		 p("Should return false. White has no network: " + testNetwork.checkNetwork(Square.WHITE).hasNetwork());
		 testNetwork.updateBoard(new Move(6,3), Square.WHITE);
		 testNetwork.updateBoard(new Move(7,3), Square.WHITE);
		 testNetwork.updateBoard(new Move(5,4), Square.BLACK);
		 p(testNetwork);

		 p("Should return false. White has no network: " + testNetwork.checkNetwork(Square.WHITE).hasNetwork());
		 testNetwork.updateBoard(new Move(1,1,5,4), Square.BLACK);
		 p(testNetwork);
		 p("Should return true. White has a network: " + testNetwork.checkNetwork(Square.WHITE).hasNetwork());
		 p("Should return 6: " + testNetwork.checkNetwork(Square.WHITE).longestPath());

		 
		 p("");
		 p("******************************");
		 p("More tests for validMoves");
		 Board testMove = new Board();
		 p("Valid moves on empty board, should be 48: " + testMove.validMoves(Square.WHITE).length());


         p("\n\n\n--------------------------------------------------------");
         p("Testing new board");
         Board b2 = new Board();
         b2.updateBoard(new Move(0,4), Square.WHITE);
         b2.updateBoard(new Move(4,4), Square.WHITE);
         b2.updateBoard(new Move(7,4), Square.WHITE);
         b2.updateBoard(new Move(2,6), Square.WHITE);
         b2.updateBoard(new Move(4,6), Square.WHITE);
         b2.updateBoard(new Move(7,6), Square.WHITE);
         b2.updateBoard(new Move(4,1), Square.WHITE);

         p(b2);
         ConnectTree n = b2.checkNetwork(Square.WHITE);
         p("The longest path is: " + n.longestPath());
         p("Has network should be false. " + n.hasNetwork());
         p("Num of nodes is: " + n.getNumOfNodes());
         
         p("\n\n\n--------------------------------------------------------");
         p("Testing chooseMove on new Board.");
         MachinePlayer testChoose = new MachinePlayer(0);
         System.out.println("add 5 black pieces, such that only one more piece at (5,0) will make a network");
		 addBlack1=new Move(3,3);
		 testChoose.forceMove(addBlack1);
		 addBlack2= new Move(5,3);
		 testChoose.forceMove(addBlack2);
		 addBlack3=new Move(2,5);
		 testChoose.forceMove(addBlack3);
		 addBlack4=new Move(5,5);
		 testChoose.forceMove(addBlack4);
		 addBlack5=new Move(3,0);
		 testChoose.forceMove(addBlack5);
		 long start = System.currentTimeMillis();
		 p(testChoose.getBoard());
		 p("" + testChoose.chooseMove());
		 Long end = System.currentTimeMillis();
		 p((end - start));
		 testChoose.forceMove(new Move (2, 7));
		 p("no network should be true: " + testChoose.getBoard().checkNetwork(Square.BLACK).hasNetwork());
		 p("Testing to block move: ");
		 MachinePlayer testBlock = new MachinePlayer(1, 0);
		 testBlock.opponentMove(addBlack1);
		 testBlock.opponentMove(addBlack2);
		 testBlock.opponentMove(addBlack3);
		 testBlock.opponentMove(addBlack4);
		 testBlock.opponentMove(addBlack5);
		 p(testBlock.getBoard());
		 p("" + testBlock.chooseMove());

		 p("");
		 p("");
		 p("-----------------------------------------------");
		 p("");
		 MachinePlayer testPlayer1 = new MachinePlayer(1); //white
		 MachinePlayer testPlayer2 = new MachinePlayer(0, 2); //black
		 /*while(!testPlayer1.getBoard().checkNetwork(Square.WHITE).hasNetwork() && !testPlayer2.getBoard().checkNetwork(Square.BLACK).hasNetwork()){
		 	Move temp = testPlayer1.chooseMove();
		 	p(temp);
		 	p(testPlayer2.opponentMove(temp));
		 	p(testPlayer1.getBoard());
		 	temp = testPlayer2.chooseMove();
		 	p(temp);
		 	p(testPlayer1.opponentMove(temp));
		 	p(testPlayer2.getBoard());
		 }*/


		 testPlayer1.forceMove(new Move(1,1));
		 testPlayer2.opponentMove(new Move(1,1));

		 testPlayer1.forceMove(new Move(1,2));
		 testPlayer2.opponentMove(new Move(1,2));

		 testPlayer1.forceMove(new Move(1,4));
		 testPlayer2.opponentMove(new Move(1,4));

		 testPlayer1.forceMove(new Move(1,5));
		 testPlayer2.opponentMove(new Move(1,5));

		 testPlayer1.forceMove(new Move(3,1));
		 testPlayer2.opponentMove(new Move(3,1));

		 testPlayer1.forceMove(new Move(3,2));
		 testPlayer2.opponentMove(new Move(3,2));

		 testPlayer1.forceMove(new Move(3,4));
		 testPlayer2.opponentMove(new Move(3,4));

		 testPlayer1.forceMove(new Move(3,6));
		 testPlayer2.opponentMove(new Move(3,6));

		 testPlayer1.forceMove(new Move(5,1));
		 testPlayer2.opponentMove(new Move(5,1));

		 testPlayer1.forceMove(new Move(5,2));
		 testPlayer2.opponentMove(new Move(5,2));

		 testPlayer1.forceMove(new Move(5,4));
		 testPlayer2.opponentMove(new Move(5,4));

		 p(testPlayer1.getBoard());
		 p(testPlayer2.getBoard());

		 testPlayer2.forceMove(new Move(2,1));
		 testPlayer1.opponentMove(new Move(2,1));

		 testPlayer2.forceMove(new Move(2,2));
		 testPlayer1.opponentMove(new Move(2,2));

		 testPlayer2.forceMove(new Move(2,4));
		 testPlayer1.opponentMove(new Move(2,4));

		 testPlayer2.forceMove(new Move(2,5));
		 testPlayer1.opponentMove(new Move(2,5));

		 testPlayer2.forceMove(new Move(4,1));
		 testPlayer1.opponentMove(new Move(4,1));

		 testPlayer2.forceMove(new Move(4,2));
		 testPlayer1.opponentMove(new Move(4,2));

		 testPlayer2.forceMove(new Move(4,4));
		 testPlayer1.opponentMove(new Move(4,4));

		 testPlayer2.forceMove(new Move(4,6));
		 testPlayer1.opponentMove(new Move(4,6));

		 testPlayer2.forceMove(new Move(6,1));
		 testPlayer1.opponentMove(new Move(6,1));

		 testPlayer2.forceMove(new Move(6,2));
		 testPlayer1.opponentMove(new Move(6,2));

		 testPlayer2.forceMove(new Move(6,4));
		 testPlayer1.opponentMove(new Move(6,4));

		 p(testPlayer1.getBoard());
		 p(testPlayer2.getBoard());
		 /*
		 while(!testPlayer1.getBoard().checkNetwork(Square.WHITE).hasNetwork() && !testPlayer2.getBoard().checkNetwork(Square.BLACK).hasNetwork()){
		 	Move temp = testPlayer1.chooseMove();
		 	p(temp);
		 	p(testPlayer2.opponentMove(temp));
		 	p(testPlayer1.getBoard());
		 	temp = testPlayer2.chooseMove();
		 	p(temp);
		 	p(testPlayer1.opponentMove(temp));
		 	p(testPlayer2.getBoard());
		 }
		 p("White has won: " + testPlayer1.getBoard().checkNetwork(Square.WHITE).hasNetwork());
		 p("black has won: " + testPlayer1.getBoard().checkNetwork(Square.BLACK).hasNetwork());
    	
		 testPlayer1 = new MachinePlayer(1);
		 testPlayer2 = new MachinePlayer(0, 2);

		 testPlayer1.forceMove(new Move(6,6));
		 testPlayer2.forceMove(new Move(1,6));

		 testPlayer1.forceMove(new Move(0,5));
		 testPlayer2.forceMove(new Move(1,5));

		 testPlayer1.forceMove(new Move(7,6));
		 testPlayer2.forceMove(new Move(6,0));

		 testPlayer1.forceMove(new Move(5,2));
		 testPlayer2.forceMove(new Move(6,5));

		 testPlayer1.forceMove(new Move(3,2));
		 testPlayer2.forceMove(new Move(2,3));

		 testPlayer1.forceMove(new Move(2,2));
		 testPlayer2.forceMove(new Move(5,6));

		 testPlayer1.forceMove(new Move(4,6));
		 testPlayer2.forceMove(new Move(4,3));

		 testPlayer1.forceMove(new Move(2,6));
		 testPlayer2.forceMove(new Move(4,2));

		 testPlayer1.forceMove(new Move(2,5));
		 testPlayer2.forceMove(new Move(1,3));

		 testPlayer1.forceMove(new Move(4,5));
		 testPlayer2.forceMove(new Move(2,0));

		 //********************************************

		 testPlayer2.opponentMove(new Move(6,6));
		 testPlayer1.opponentMove(new Move(1,6));

		 testPlayer2.opponentMove(new Move(0,5));
		 testPlayer1.opponentMove(new Move(1,5));

		 testPlayer2.opponentMove(new Move(7,6));
		 testPlayer1.opponentMove(new Move(6,0));

		 testPlayer2.opponentMove(new Move(5,2));
		 testPlayer1.opponentMove(new Move(6,5));

		 testPlayer2.opponentMove(new Move(3,2));
		 testPlayer1.opponentMove(new Move(2,3));

		 testPlayer2.opponentMove(new Move(2,2));
		 testPlayer1.opponentMove(new Move(5,6));

		 testPlayer2.opponentMove(new Move(4,6));
		 testPlayer1.opponentMove(new Move(4,3));

		 testPlayer2.opponentMove(new Move(2,6));
		 testPlayer1.opponentMove(new Move(4,2));

		 testPlayer2.opponentMove(new Move(2,5));
		 testPlayer1.opponentMove(new Move(1,3));

		 testPlayer2.opponentMove(new Move(4,5));
		 testPlayer1.opponentMove(new Move(2,0));


		 p(testPlayer1.getBoard());
		 p(testPlayer2.getBoard());
		*/
		 //p("from 6,6 to 6,3: " + testPlayer1.forceMove(new Move(6,3,6,6)));
		 /* 
		 p("****************************");
		 p("");
		 p("testing times for various depths in Miliseconds: ");
		 Long time;
		 p("depth of 1:");
		 MachinePlayer depth = new MachinePlayer(1, 1);
		 testPlayer1.stepDepth = 1;
		 start = System.currentTimeMillis();
		 testPlayer1.chooseMove();
		 end = System.currentTimeMillis();
		 time = (end - start);
		 p("Step Move: " + time);
		 start = System.currentTimeMillis();
		 depth.chooseMove();
		 end = System.currentTimeMillis();
		 time = (end - start);
		 p("first Move: " + time);
		 p("************************");

		 p("depth of 2:");
		 testPlayer2.stepDepth = 2;
		 start = System.currentTimeMillis();
		 testPlayer2.chooseMove();
		 end = System.currentTimeMillis();
		 time = (end - start);
		 p("Step Move: " + time);
		 depth = new MachinePlayer(1, 2);
		 start = System.currentTimeMillis();
		 depth.chooseMove();
		 end = System.currentTimeMillis();
		 time = (end - start);
		 p("first Move: " + time);
		 p("************************");

		 p("depth of 3:");
		 testPlayer1.stepDepth = 3;
		 start = System.currentTimeMillis();
		 testPlayer1.chooseMove();
		 end = System.currentTimeMillis();
		 time = (end - start);
		 p("Step Move: " + time);
		 depth = new MachinePlayer(1, 3);
		 start = System.currentTimeMillis();
		 depth.chooseMove();
		 end = System.currentTimeMillis();
		 time = (end - start);
		 p("first Move: " + time);
		 p("************************");

		 p("depth of 4:");
		 testPlayer2.stepDepth = 4;
		 start = System.currentTimeMillis();
		 testPlayer2.chooseMove();
		 end = System.currentTimeMillis();
		 time = (end - start);
		 p("Step Move: " + time);
		 depth = new MachinePlayer(1, 4);
		 start = System.currentTimeMillis();
		 depth.chooseMove();
		 end = System.currentTimeMillis();
		 time = (end - start);
		 p("first Move: " + time);
		 p("************************");

		 p("depth of 5:");
		 depth = new MachinePlayer(1, 5);
		 start = System.currentTimeMillis();
		 depth.chooseMove();
		 end = System.currentTimeMillis();
		 time = (end - start);
		 p("first Move: " + time);
		 p("************************");

		 p("depth of 6:");
		 depth = new MachinePlayer(1, 6);
		 start = System.currentTimeMillis();
		 depth.chooseMove();
		 end = System.currentTimeMillis();
		 time = (end - start);
		 p("first Move: " + time);
		 p("************************");
	*/
    }
}
