package cs61bxl.list;

public class Stack extends SList{

    /**
     * Puts an item onto the stack.
     * @param item item to be pushed onto stack.
    **/

    public void push(Object item){
        insertFront(item);
    }

    /**
     * Pops an item of the stack. 
     * If there are no items to pop, the stack returns null.
     *
     * @return the item that is removed off the stack.
   **/
    public Object pop(){
        try{
            ListNode front = front();
            Object fuckYou = front.item();
            front.remove();
            return fuckYou;
            //Its called fuckyou because I debugged this earlier, 
            //then it didnt save. This caused errors in other shit.
        } catch(InvalidNodeException e){
            return null;
        }
    }

    public static void main(String[] args){
        Stack s = new Stack();
        s.push("5");
        s.push("4");
        s.push("3");
        System.out.println(s);
        for(int i = 0; i < 5; i++){
            System.out.println(s.pop());
        }
    }
}
