/**
 * An implementation of the AutoCompleteInterface using a DLB Trie.
 */

//these imports were provided on the assignmnet, but are never used. Honestly not sure why they're here
import java.util.ArrayList;
import javax.sound.sampled.AudioFileFormat;

 public class AutoComplete implements AutoCompleteInterface {

  private DLBNode root;
  private StringBuilder currentPrefix;
  private DLBNode currentNode;
  private int wordOffset = 0;

  public AutoComplete(){
    root = null;
    currentPrefix = new StringBuilder();
    currentNode = null;
  }

  /**
   * Adds a word to the dictionary in O(word.length()) time
   * @param word the String to be added to the dictionary
   * @return true if add is successful, false if word already exists
   * @throws IllegalArgumentException if word is the empty string
   */
    public boolean add(String word){

      if (word == "") throw new IllegalArgumentException("calls add() with a empty key");
      //create a stringbuilder for the word parameter
      StringBuilder sbTemp = new StringBuilder(word);

      //if the root is not initialized, creat the root of the DLB Trie
      if (root == null){
        root = new DLBNode(sbTemp.charAt(0));
      }

      /*
      * It is important that currentNode never becomes null so it is always pointing to 
      * some spot in the array, so we create a temporary node here to be safe. The same 
      * process continues through the rest of the program.
      */
      DLBNode curNode = root;
      DLBNode temp = null;
      while (curNode != null){
        //check if the data matches, if not move to the next sibling if it exists
        if (curNode.data == sbTemp.charAt(0)){
          return add(root,0, sbTemp);
        }else{
          //if the sibling doesn't exist, create it
          if (curNode.nextSibling == null){
            temp = new DLBNode(sbTemp.charAt(0));
            temp.previousSibling = curNode;
            curNode.nextSibling = temp;
          }
          //traverse to the next sibling
          curNode = curNode.nextSibling;
        }//end else
      }//end while
      return false;
    }//end method

    private boolean add(DLBNode node, int pos, StringBuilder word){

      char curLet = word.charAt(pos);
      DLBNode nextNode = null;

      //if the current node is null. Hypothetically this should never happen.
      if (node == null){
        System.out.println("Node variable in add was null!");
        return false;

      //if the data matches, move to the next node if it exists
      }else if (node.data == curLet){

        //check if letters can still be added
        if (pos < word.length()-1){
          //if the child does not exist, create it and link it
          if (node.child == null){
            nextNode = new DLBNode(word.charAt(pos+1));
            nextNode.parent = node;
            node.child = nextNode;

            //if the created child is the last node, declare it as a word and return
            if (pos+1 == word.length()-1){
              nextNode.isWord = true;
              node.size++;
              nextNode.size++;
              return true;
            }//continues to the add call

          }else nextNode = node.child;

          //recurse to the next node in the trie
          if (add(nextNode, pos+1, word)){
            node.size++;
            return true;
          }
        //return false if the word exists
        }else return false;

      //check for siblings, if there aren't any siblings create one
      // if there are siblings, recurse to the sibling.
      }else{
        if (node.nextSibling == null){
          //create the sibling with the current letter of the word and link it
          nextNode = new DLBNode(word.charAt(pos));
          nextNode.previousSibling = node;
          node.nextSibling = nextNode;

          //if the created sibling is the last letter, declare as word
          if (pos == word.length()-1){
            nextNode.isWord = true;
            nextNode.size++;
            return true;
          }
        }else nextNode = node.nextSibling;

        //Do not increment size here since we are traversing across the sibling list
        if (add(nextNode, pos, word)) return true;
      }//end else
      return false;
    }//end method

  /**
   * appends the character c to the current prefix in O(1) time. This method 
   * doesn't modify the dictionary.
   * @param c: the character to append
   * @return true if the current prefix after appending c is a prefix to a word 
   * in the dictionary and false otherwise
   */
    public boolean advance(char c){
      currentPrefix.append(c);
      DLBNode tempNode = currentNode;

      //check if currentPrefix is invalid
      if (wordOffset > 0){
        wordOffset++;
        return false;
      }else{
        //if currentNode is null then this is the first call to advance
        //otherwise move to the child (next letter) if there is one
        if (currentNode == null){
          currentNode = root;
          tempNode = currentNode;
        }
        //if the child is null, then currentPrefix is now invalid
        else if (currentNode.child == null){
          wordOffset++;
          return false;
        }else tempNode = currentNode.child;
        //traversal while loop
        while (tempNode != null){
          //if the data matches, append the letter and return true if the word is a prefix
          if (tempNode.data == c){
            currentNode = tempNode;
            return true;
          //move across the sibling list if it exists. if it doesn't, currentPrefix is invalid
          }else{
            if (tempNode.nextSibling == null){
              wordOffset++;
              return false;
            }else tempNode = tempNode.nextSibling;
          }
        }//end while
        return false;
      }//end else
    }

  /**
   * removes the last character from the current prefix in O(1) time. This 
   * method doesn't modify the dictionary.
   * @throws IllegalStateException if the current prefix is the empty string
   */
    public void retreat(){
      if (currentPrefix.length()==0) throw new IllegalStateException();

      //delete the character and check if currentPrefix is now valid
      currentPrefix.deleteCharAt(currentPrefix.length()-1);
      if (currentPrefix.length() == 0){
        reset();
        return;
      }

      //if the word is valid for the dictionary, operate normally
      if(wordOffset == 0){
        while (currentNode!=null){
          //if the parent exists, move to it and return
          if (currentNode.parent != null){
            currentNode = currentNode.parent;
            return;
          }
          //else if the previous sibling exists, move to it and continue the loop
          else if (currentNode.previousSibling != null) currentNode = currentNode.previousSibling;

          //if both the parent and sibling are null, then we are at the root node of the trie, 
          // and shouldn't be in this while loop in the first place
        }//end while
      }else wordOffset--;

      return;
    }

  /**
   * resets the current prefix to the empty string in O(1) time
   */
    public void reset(){
      currentPrefix = new StringBuilder();
      currentNode = null;
      wordOffset = 0;
    }
    
  /**
   * @return true if the current prefix is a word in the dictionary and false
   * otherwise
   */
    public boolean isWord(){
      if (wordOffset == 0){
        return currentNode.isWord;
      }else return false;
    }

  /**
   * adds the current prefix as a word to the dictionary (if not already a word)
   * The running time is O(length of the current prefix). 
   */
    public void add(){
      if (isWord() == false){
        add(currentPrefix.toString());

        int index;
        //once the word is added, move currentNode to the end of the new word
        DLBNode tempNode = currentNode;
        while (wordOffset>0){
          //set our index int
          index = currentPrefix.length()-wordOffset;
          //move to the child if it exists
          if (tempNode.child != null){
            tempNode = tempNode.child;
            //traverse across the siblings linkedlist
            while(tempNode!=null){
              //check for matching char data 
              if (currentPrefix.charAt(index) == tempNode.data){
                wordOffset--;
                break;
              }else tempNode = tempNode.nextSibling;
            }//end inner while
          }//end child if
        }//end outer while
        currentNode = tempNode;
      }//end main if
      return;
    }//end method

  /** 
   * @return the number of words in the dictionary that start with the current 
   * prefix (including the current prefix if it is a word). The running time is 
   * O(1).
   */
    public int getNumberOfPredictions(){
      if (wordOffset > 0){
        return 0;
      }else if(currentNode == null){
        return 0;
      }else return currentNode.size;
    }
  
  /**
   * retrieves one word prediction for the current prefix. The running time is 
   * O(prediction.length()-current prefix.length())
   * @return a String or null if no predictions exist for the current prefix
   */
    public String retrievePrediction(){
      DLBNode tempNode = currentNode;
      StringBuilder temp = new StringBuilder(currentPrefix);
      if (wordOffset == 0){
        while (tempNode.isWord == false){
          //Move to the child of the tempNode if it exists
          if (tempNode.child != null){
            tempNode = tempNode.child;
            temp.append(tempNode.data);
          }else{
            return null;
          }

        }//end while
        return temp.toString();
      }//end if
      return null;
    }//end method


/*
 * =======================================================================
 * I, Sam Durigon, did not write anything below this point.
 * =======================================================================
 */


  /* ==============================
   * Helper methods for debugging.
   * ==============================
   */

  //print the subtrie rooted at the node at the end of the start String
  public void printTrie(String start){
    System.out.println("==================== START: DLB Trie Starting from \""+ start + "\" ====================");
    if(start.equals("")){
      printTrie(root, 0);
    } else {
      DLBNode startNode = getNode(root, start, 0);
      if(startNode != null){
        printTrie(startNode.child, 0);
      }
    }
    
    System.out.println("==================== END: DLB Trie Starting from \""+ start + "\" ====================");
  }

  //a helper method for printTrie
  private void printTrie(DLBNode node, int depth){
    if(node != null){
      for(int i=0; i<depth; i++){
        System.out.print(" ");
      }
      System.out.print(node.data);
      if(node.isWord){
        System.out.print(" *");
      }
      System.out.println(" (" + node.size + ")");
      printTrie(node.child, depth+1);
      printTrie(node.nextSibling, depth);
    }
  }

  //return a pointer to the node at the end of the start String.
  private DLBNode getNode(DLBNode node, String start, int index){
    if(start.length() == 0){
      return node;
    }
    DLBNode result = node;
    if(node != null){
      if((index < start.length()-1) && (node.data == start.charAt(index))) {
          result = getNode(node.child, start, index+1);
      } else if((index == start.length()-1) && (node.data == start.charAt(index))) {
          result = node;
      } else {
          result = getNode(node.nextSibling, start, index);
      }
    }
    return result;
  } 

  //The DLB node class
  private class DLBNode{
    private char data;
    private int size;
    private boolean isWord;
    private DLBNode nextSibling;
    private DLBNode previousSibling;
    private DLBNode child;
    private DLBNode parent;

    private DLBNode(char data){
        this.data = data;
        size = 0;
        isWord = false;
        nextSibling = previousSibling = child = parent = null;
    }
  }
}
