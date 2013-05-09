/* HashTableChained.java */

package cs61bxl.dict;
import cs61bxl.list.*;

/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

  /**
   *  Place any data fields here.
   **/

    int size;
    List[] arr;

  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

    //Load factor of 1
  public HashTableChained(int sizeEstimate) {
    size = 0;
    int sizeTable = ((int) (sizeEstimate * 1.5));
    while(!prime(sizeTable)){
      sizeTable++;
    }
    if(sizeTable < 0){
      sizeTable = 2000000011;
    }
    arr = new SList[sizeTable];
  }

  public boolean prime(int p){
    for(int i = 2; i < Math.sqrt(p); i++){
      if(p%i == 0){
        return false;
      }
    }
    return true;
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
      size = 0;
      arr = new SList[101];
      for(int i = 0; i < arr.length; i++)
          arr[i] = new SList();
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
    // Replace the following line with your solution.
    return Math.abs(((131*code + 4093) % 2000000011) % arr.length);
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    return size;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    // Replace the following line with your solution.
    return size == 0;
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key cannot exist. It will get replaced on
   *  each insert.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
      if(find(key) != null){
          remove(key);
      }
      int pos = compFunction(key.hashCode());
      List theList;
      if(arr[pos] == null){
        arr[pos] = new SList();
        theList = arr[pos];
      }
      else{
        theList = arr[pos];
      }
      Entry holder = new Entry();
      holder.key = key;
      holder.value = value;
      theList.insertFront(holder);
      size++;
      if((size / arr.length) > .75){
        resize();
      }
      return holder;
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
    // Replace the following line with your solution.
    try{
        if(isEmpty()){
            return null;
        }
        int pos = compFunction(key.hashCode());
        List theList = arr[pos];
        if(theList == null){
            return null;
        }
        ListNode node = theList.front();
        if(node == null){
            return null;
        }
        Entry currEntry = (Entry) node.item();
        while(!currEntry.key.equals(key)){
            node = node.next();
            currEntry = (Entry) node.item();
        }
        return currEntry;
    } catch(InvalidNodeException x) {
        return null;
    }

  }

  public void resize(){
    List[] tempTable = arr;
    int sizeTable = size * 2;
    if(size == 0){
        sizeTable = 2;
    }
    while(!prime(sizeTable)){
      sizeTable++;
    }
    size = 0;
    arr = new List[sizeTable];
    for(int i = 0; i < tempTable.length; i++){
      try{
        if(tempTable[i] != null){
          if(tempTable[i].length() > 0){
            ListNode tempNode = tempTable[i].front();
            do{
              insert(((Entry) tempNode.item()).key(), ((Entry)tempNode.item()).value());
              tempNode = tempNode.next();
            }while(tempNode != null);
          }
        }
      }catch(InvalidNodeException e){}
    }
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
    // Replace the following line with your solution.
    try{
        int pos = compFunction(key.hashCode());
        List theList = arr[pos];
        if(theList == null){
            return null;
        }
        ListNode node = theList.front();
        if(node == null){
            return null;
        }
        Entry currEntry = (Entry) node.item();
        while(!currEntry.key.equals(key)){
            node = node.next();
            currEntry = (Entry) node.item();
        }
        node.remove();
        size--;
        if((size / arr.length) < .25){
          resize();
        }
        return currEntry;
    } catch(InvalidNodeException x) {
        return null;
    }
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
      arr = new SList[arr.length];
      size = 0;
      for(int i = 0; i < arr.length; i++)
          arr[i] = new SList();

  }

  int times = 0;

  public void test(){
      int count = 0;
      for(int i = 0; i < arr.length ; i++){
          System.out.print("Hash " + i + " : ");
          boolean subtract = false;
          if(arr[i] != null){
            for(int j = 0; j < arr[i].length(); j++){
                System.out.print("X");
                count++;
                subtract = true;
            }
          }
          if(subtract)
              count--;
          System.out.println();
      }
      System.out.println("-----------------------------");
      System.out.println("I have: " + count + " collisions");
  }

    public static boolean[] getPrimes(int max){
        boolean[] isPrime = new boolean[max + 1];
        int i = 2;
        for(int k = 0; k < isPrime.length; k++)
            isPrime[k] = true;
        isPrime[0]  = false;
        isPrime[1] = false;
        while(i < isPrime.length){
            if(isPrime[i]){
                for(int j = i+i; j < isPrime.length; j += i)
                    isPrime[j] = false;
            }
            i++;
        }
        return isPrime;
    }

}
