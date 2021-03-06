package cs61bxy.tree;

import cs61bxy.Square;
import cs61bxl.dict.*;

public class ConnectTreeNode{
    protected Square square; // The square this node represents.
    protected ConnectTreeNode parent; //The parent of this node.
    protected ConnectTreeNode firstChild; //THe first child of this node. Null if doesnt have one
    protected ConnectTreeNode nextSibling; //The sibling of this node. Null if doesnt have one
    protected ConnectTree tree; //The tree that this node has/
    protected int depth; //The depth of this node.
    protected int direction; //The direction traveled to get to this node. If starting node, then -1;

    /**
     * Constructor that creates a ConnectTreeNode with all fields set to
     * null or 0 except for tree.
     *
     * @param tree the tree that the node is part of.
    **/
    public ConnectTreeNode(ConnectTree tree){
        square = null;
        parent = null;
        firstChild = null;
        nextSibling = null;
        this.tree = tree;
        depth = 0;
        direction = -1;
    }

    /**
     * return the depth of this node. 
     * @return the depth of this node;
     **/
    public int getDepth(){
        return depth;
    }

    /**
     * gets the parent of this node
     * @return returns parent of this node. 
     **/
    public ConnectTreeNode getParent(){
        return parent;
    }

    /**
     * returns square that this node represents.
     * @return the square this node repressents/hold
     **/
    public Square getSquare(){
        return square;
    }

    /**
     * @return first child of this node. Null if does not have one.
     **/
    public ConnectTreeNode getFirstChild(){
        return firstChild;
    }

    /**
     * @return next Sibling of this node. Null if does not have one.
     **/
    public ConnectTreeNode getSibling(){
        return nextSibling;
    }

    /**
     * @param tree the tree that the node is part of.
     * @param square the square that this node represents.
     *
     * @return a connectTreeNode that has no children, siblings, or depth
     *      but with given tree and square properties.
    **/
    public ConnectTreeNode(ConnectTree tree, Square square){
        this(tree);
        this.square = square;
    }

    /**
     *  @param tree the tree the node is part of.
     *  @param square the square the node represents.
     *  @param parent the parent of this node.
     *
     *  Returns a connect Tree Node with the given tree, square, and parent properties.
     *  Depth and children are set to 0 and null, respectively;
    **/
    public ConnectTreeNode(ConnectTree tree, Square square, ConnectTreeNode parent){
        this(tree, square);
        this.parent = parent;
    }

    /**
     * @param tree the tree node is part of.
     * @param square the square the node represents.
     * @param parent the parent of this node.
     * @param depth the current depth of the node.
     *
     * Constructs a new ConnectTreeNode with no children but above properties;
    **/
    public ConnectTreeNode(ConnectTree tree, Square square ,ConnectTreeNode parent, int depth){
        this(tree, square, parent);
        this.depth = depth;
    }

    /**
     * @param tree the tree node is part of.
     * @param square the square the node represents.
     * @param parent the parent of this node.
     * @param depth the current depth of the node.
     * @param dir the direction that was traveled to this node.
     *
     * Constructs a new ConnectTreeNode with no children but above properties;
    **/
    public ConnectTreeNode(ConnectTree tree, Square square, ConnectTreeNode parent, int depth, int dir){
        this(tree, square, parent, depth);
        this.direction = dir;
    }

    /**
     * Checks if node has a sibling.
     * @return true if node has sibling, false otherwise
    **/
    public boolean hasSibling(){
        return (nextSibling != null);
    }

    /**
     * Checks if node has a child.
     * @return true if node has a child, false otherwise;
    */
    public boolean hasChild(){
        return (firstChild != null);
    }

    /**
     * Adds an array of Siblings to the current node.
     * If the node already has siblings, then it travels to the sibling node
     * and adds children to it. Adds the siblings in sequential order from 0 to arr.
     * length -1
     *
     * Also, if any objects in the array are null, it will NOT be added to the siblings.
     * 
     * @param arr is an array of objects of class Square to add.
    **/
    public void addArrayOfSiblings(Square[] arr){
        ConnectTreeNode currNode = this;

        while(currNode.hasSibling()){
            currNode = currNode.nextSibling;
        }

        for(int i = 0; i < arr.length; i++){
            if(arr[i] == null){
                continue;
            }
            ConnectTreeNode newNode = new ConnectTreeNode(this.tree, arr[i], this.parent, this.depth);
            currNode.nextSibling = newNode;
            currNode = currNode.nextSibling;
            this.tree.numOfNodes++;
        }
    }

    /**
     * Adds an array of children to the current node
     * If the current node has children, it then adds these children to the last child's 
     * siblings.
     *
     * If the array has any null references, they do NOT get added to the tree.
     *
     * @param arr the array of children to add to the node.
     * @param startingPos boolean that determines whether these children are 
     *          starting positions or not. True if start, else false
    **/
    public void addArrayOfChildren(Square[] arr, boolean startingPos){
        ConnectTreeNode currNode = this.firstChild;

        while(currNode != null){
            currNode = currNode.nextSibling;
        }

        for(int i = 0; i < arr.length; i++){

            if(arr[i] == null){
                continue;
            }

            int dir = i;
            if(startingPos){
                dir = -1;
            }

            if(currNode == null){
                
                    
                this.firstChild = new ConnectTreeNode(this.tree, arr[i], this,
                        this.depth + 1, dir);
                currNode = this.firstChild;
            } else {
                currNode.nextSibling =  new ConnectTreeNode(this.tree, arr[i], this,
                        this.depth + 1, dir);
                currNode = currNode.nextSibling;
            }

            this.tree.numOfNodes++;

            if(this.tree.longestPath < this.depth + 1){
                this.tree.longestPath = this.depth + 1;
            }
        }
    }

    /**
     * Adds the connections as children to this node. Removes the null squares,
     * the squares that  are in the hash table, and the squares with the same direction 
     *
     * @param arr the connections that are being added as children to this node.
     * @param ht the hashtable that contains all previous visited nodes, or the nodes
     *        that you do not want added.
     **/
    public void addConnections(Square[] arr, HashTableChained ht){
        Square[] copiedArr = new Square[arr.length];
        for(int i = 0; i < copiedArr.length; i++){
            if(i != direction && arr[i] != null && ht.find(arr[i]) == null){
                copiedArr[i] = arr[i];
            }
        }

        addArrayOfChildren(copiedArr, false);
    }
        

    /**
     * Prints this node and its children and siblings.
     * The format will be: 
     *  THIS --- SIB --- SIB ...
     *  |
     *  |
     *  Child -- Child --- Child ...
     *
     *  @return string of the above format.
     **/
    public String toString(){
        String txt = "";
        ConnectTreeNode currNode = this;

        if(this.square != null){
            txt += this.square.getCoords();
        }

        while(currNode.hasSibling()){
            currNode = currNode.nextSibling;
            txt = txt + " --- " + currNode.square.getCoords();
        }

        txt += "\n|\n|\n";

        ConnectTreeNode currChild = this.firstChild;
        while(currChild != null){
            txt = txt + currChild.square.getCoords() + "---" ;
            currChild = currChild.nextSibling;
        }

        return txt;
    }

    /**
     * Uses the square hashcode for this nodes hash code
     * @return square's hashcode, which is its location in terms of XY
     **/
    public int hashCode(){
        return square.hashCode();
    }

    public static void main(String[] args){
        ConnectTree tree = new ConnectTree(5);
        Square[] child = new Square[5];
        Square[] sib = new Square[5]; 

        for(int i = 0; i < 5; i++){
            child[i] = new Square(4, i, 1);
            sib[i] = new Square(2, i, 1);
        }

        child[3] = null;

        tree.root.addArrayOfChildren(child, false);
        tree.root.addArrayOfSiblings(sib);
        tree.root.firstChild.addArrayOfChildren(child, false);
        tree.root.addArrayOfSiblings(sib);


        System.out.println(tree.root);
        System.out.println(tree.root.firstChild);
        System.out.println("Should be 2: " + tree.longestPath);
    }



}
