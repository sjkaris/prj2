package tests;
import player.*;

public class testSquare{

    public static void p(Object o){
        System.out.println(o);
    }

    public static void main(String args[]){
        Board b = new Board();
        System.out.println("Printing blank board.\n" + b);

        System.out.println("Adding three black pieces while testing update board.");
        p("Adding to 1,2; 2,2; 4,4");
        b.updateBoard(new Move(1,2), Square.BLACK);
        b.updateBoard(new Move(2,2), Square.BLACK);
        b.updateBoard(new Move(4,4), Square.BLACK);
        p(b);

        p("Adding white pieces to 1,6 6,1 and 5,5");

        b.updateBoard(new Move(1,6), Square.WHITE);
        b.updateBoard(new Move(6,1), Square.WHITE);
        b.updateBoard(new Move(5,5), Square.WHITE);
        p(b);

        System.out.println("Checking connections for 1,2");

        for(Square i : b.getSquare(1,2).getConnections()){
            if(i != null)
                i.printCoords();
        }
        
        System.out.println("Checking connections for 2,2");
        for(Square i : b.getSquare(2,2).getConnections()){
            if(i != null)
                i.printCoords();
        }

        System.out.println("Checking connections for 4,4");
        for(Square i : b.getSquare(4,4).getConnections()){
            if(i != null)
                i.printCoords();
        }

        p("checking connections with an oppositely colored piece in the way.");
        b.updateBoard(new Move(3,3), Square.WHITE);
        p(b);
        System.out.println("Checking connections for 2,2");
        for(Square i : b.getSquare(2,2).getConnections()){
            if(i != null)
                i.printCoords();
        }
        p("");
        //p(b.getColor(6,1));

        System.out.println("Testing checks of placement rules.");
        System.out.println("Should be false: " + b.getSquare(0,3).validForAdj(Square.BLACK));
        System.out.println("Should be false: " + b.getSquare(2,3).validForAdj(Square.BLACK));
        System.out.println("Should be true: " + b.getSquare(2,3).validForAdj(Square.WHITE));
        System.out.println("Should be true: " + b.getSquare(0,6).validForAdj(Square.WHITE));


        System.out.println("Testing checkMove: ");

      
    }
}
