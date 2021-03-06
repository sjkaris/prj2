package tree;

import player.Square;

public class ConnectTree {
    protected boolean isWinner; //Represents if tree has a network, or is a winner
    protected int color; //Represents the color of the paths of the pieces.
    protected int longestPath; //Represents the longest path. Used in eval board
    protected ConnectTreeNode root; //the root of the node. 
    protected int numOfNodes; //Represrents the sum of all path lengths.

    /**
     * Constructs the connectTree which is used for checkNetwork and eval board;
     * This tree will eventually represents a winning network path or all possible paths
     * from one goal tthe next. 
     *
     * Invariants:
     *  root node will never contain a square. It just holds stuff together. 
     *  root will never have siblings. It can only have children who have siblings.
     *  Once isWinner is set to true, the tree will stop constructing.
     *
     * @param color the color of the network paths we are checking. 
     **/ 
    public ConnectTree(int color){
        root = new ConnectTreeNode(this);
        isWinner = false;
        this.color = color;
        longestPath = 0;
        numOfNodes = 0;
    }

    /**
     * Getter method that returns the root node.
     * @return the root node
     **/
    public ConnectTreeNode getRoot(){
        return root;
    }

    /**
     * Getter method for isWinner. 
     * @return return true if there is a network. False otherwise;
     **/
    public boolean hasNetwork(){
        return isWinner;
    }

    /**
     * Getter method for numOfNodes
     * @return the number of nodes in the tree
     **/
    public int getNumOfNodes(){
        return numOfNodes;
    }

    /**
     * Returns the length of the longest path;
     * @return the length of longest path 
     **/
    public int longestPath(){
        return longestPath;
    }

    /**
     * Setter method. Sets isWinner to true if there is a network.
     * @param bool whether to set isWinner to t/f
     **/
    public void setWinner(boolean bool){
        isWinner = bool;
    }
}
