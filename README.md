# CS 1501 – Algorithm Implementation – Assignment #2

Due: Friday November 4th @ 11:59pm on Gradescope

Late submission deadline: Sunday November 6th @11:59pm with 10% penalty per late day

You should submit the Java file `AutoComplete.java` to GradeScope (the link is on Canvas). You must also submit a writeup named `a2.md` and an Assignment Information Sheet `InfoSheet.md` as described below.

## Table of Contents

- [Overview](#overview)
- [Background](#background)
- [Details](#Details)
- [Testing and Debugging](#Testing-and-Debugging)
- [Hints](#hints)
- [Writeup](#writeup)
- [Submission Requirements](#submission-requirements)
- [Rubrics](#rubrics)

## Overview

* __Purpose__:  To implement a simple automatic word-completion system.
* __*Task 1*__: Implement algorithms for DLB insertion and traversal.
* __*Task 2*__: Implement an algorithm for retrieving one word prediction.

## Background

Autocomplete is a commonly used feature in mobile phones, text editors, and search engines. As a user types in letters, the system shows a list of word predictions to help the user complete the word they are typing. The core of an efficient autocompletion system is a fast algorithm for retrieving word predictions based on the user input. The word predictions are all the words (in a given dictionary) that start with what the user has typed so far (i.e., the list of words for which the user's input is a prefix).

In this assignment we will build a simple autocompletion system using a DLB trie which allows 
the user to add a new word to its dictionary when none of the predictions are selected by the user.

The `AutoCompleteInterface` defines a Java interface for a dictionary that provides word predictions for such an autocompletion system. Besides storing a set of words, the dictionary keeps track of a prefix String, which starts with the empty String. You will implement methods to add one character to and delete the last character of that prefix String. You will also implement methods that use that prefix String for various operations, such as checking if the prefix String is one of the words in the dictionary, retrieving the number of words that have the prefix String as a prefix, and adding the prefix String to the dictionary.

## Details

In the first part of this assignment, you will implement algorithms for:

- inserting a `StringBuilder` object into a DLB trie by filling in the `add(String word)` method below. You may want to add and call a private recursive helper method.

```java
/**
 * Adds a word to the dictionary in O(word.length()) time
 * @param word the String to be added to the dictionary
 * @return true if add is successful, false if word already exists
 * @throws IllegalArgumentException if word is the empty string
 */
  public boolean add(String word){
    //TODO: implement this method
    return false;
  }

```

The `DLBNode` class is already defined for you in `AutoComplete.java`. You will use a doubly-linked list of siblings, a child reference, and an upwards reference to the node's parent.
The `isWord` field of a node is true if and only if the node is at the end of a word in the dictionary.
The `size` field keeps track of the number of nodes with `isWord==true` in the subtree rooted at the node, including the node itself. The `add` method should update the `size` field for some of the nodes that it goes over. You will need to think about for which nodes `size` should be incremented.


```java
//The DLB node class
private class DLBNode {
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
 ```


The next set of the methods modify the prefix String maintained by the dictionary. These methods are: `advance(char c)` for appending a character to the prefix String, `retreat()` for deleting the last character, and `reset()` for resetting it back to the empty String. More details about these methods can be found below. Your code should maintain a `DLBNode currentNode` reference to always point to the DLB node at the end of the prefix String. `currentNode` is null when the prefix String is `""`, moves over the sibling list of the root node upon the first call to `advance`, moves possibly sideways then down the trie with each further call to `advance`, moves possibly sideways then up the trie with `retreat` until resetting to `null` when the prefix String becomes empty, and resets to `null` with `reset`.

```java
/**
 * appends the character c to the current prefix in O(alphabet size) time. This method 
 * doesn't modify the dictionary.
 * @param c: the character to append
 * @return true if the current prefix after appending c is a prefix to a word 
 * in the dictionary and false otherwise
 */
  public boolean advance(char c){
    //TODO: implement this method
    return false;
  }

/**
 * removes the last character from the current prefix in O(alphabet size) time. This 
 * method doesn't modify the dictionary.
 * @throws IllegalStateException if the current prefix is the empty string
 */
  public void retreat(){
    //TODO: implement this method
  }

/**
 * resets the current prefix to the empty string in O(1) time
 */
  public void reset(){
    //TODO: implement this method
  }
```

The last set of methods operate on the prefix String by checking whether it is a word in the dictionary (`isWord()`), adding it if not (`add()`), retrieving the number predictions for the prefix String (`getNumberOfPredictions`), and retrieving one of the predictions (if any) (`retrievePrediction`). Retrieving the number predictions should be as simple as returning the `size` field of the `currentNode`. The `add()` method should increment the `size` field for some of the nodes (which ones?). (Hint: you may need to climb up the trie.)

```java
/**
 * @return true if the current prefix is a word in the dictionary and false
 * otherwise
 */
  public boolean isWord(){
    //TODO: implement this method
    return false;
  }

/**
 * adds the current prefix as a word to the dictionary (if not already a word)
 * The running time is O(length of the current prefix). 
 */
  public void add(){
    //TODO: implement this method
  }

/** 
 * @return the number of words in the dictionary that start with the current 
 * prefix (including the current prefix if it is a word). The running time is 
 * O(1).
 */
  public int getNumberOfPredictions(){
    //TODO: implement this method
    return 0;
  }

/**
 * retrieves one word prediction for the current prefix. The running time is 
 * O(prediction.length()-current prefix.length())
 * @return a String or null if no predictions exist for the current prefix
 */
  public String retrievePrediction(){
    //TODO: implement this method
    return null;
  }
```

## Testing and Debugging

To test your code, use `A2Test.java` and develop your own test cases. This program needs a dictionary file. I have included `dict8.txt`, which is the same dictionary that we used in Assignment 1. I highly recommend that you start by creating your own small dictionary (e.g., similar to `small.txt`) to test your code. Sample output is provided below. The file `small.txt` contains the following words:

```
abc
a
aba
xyz
x
efg
ef
```

```
java A2Test small.txt
Adding abc
==================== START: DLB Trie Starting from "" ====================
a (1)
 b (1)
  c * (1)
==================== END: DLB Trie Starting from "" ====================
Adding a
==================== START: DLB Trie Starting from "" ====================
a * (2)
 b (1)
  c * (1)
==================== END: DLB Trie Starting from "" ====================
Adding aba
==================== START: DLB Trie Starting from "" ====================
a * (3)
 b (2)
  c * (1)
  a * (1)
==================== END: DLB Trie Starting from "" ====================
Adding xyz
==================== START: DLB Trie Starting from "" ====================
a * (3)
 b (2)
  c * (1)
  a * (1)
x (1)
 y (1)
  z * (1)
==================== END: DLB Trie Starting from "" ====================
Adding x
==================== START: DLB Trie Starting from "" ====================
a * (3)
 b (2)
  c * (1)
  a * (1)
x * (2)
 y (1)
  z * (1)
==================== END: DLB Trie Starting from "" ====================
Adding efg
==================== START: DLB Trie Starting from "" ====================
a * (3)
 b (2)
  c * (1)
  a * (1)
x * (2)
 y (1)
  z * (1)
e (1)
 f (1)
  g * (1)
==================== END: DLB Trie Starting from "" ====================
Adding ef
==================== START: DLB Trie Starting from "" ====================
a * (3)
 b (2)
  c * (1)
  a * (1)
x * (2)
 y (1)
  z * (1)
e (2)
 f * (2)
  g * (1)
==================== END: DLB Trie Starting from "" ====================
Testing autocomplete:
Enter one letter then press enter to get auto-complete suggestions (enter < to delete last character and . to stop) ...
a
a --> a (3 predictions total)
b
ab --> abc (2 predictions total)
c
abc --> abc (1 predictions total)
d
No predictions found for abcd
.
Do you want to add abcd? (y/n)
y
Do you want to continue? (y/n)
y
Enter one letter then press enter to get auto-complete suggestions (enter < to delete last character and . to stop) ...
e
e --> ef (2 predictions total)
f
ef --> ef (2 predictions total)
g
efg --> efg (1 predictions total)
h
No predictions found for efgh
.
Do you want to add efgh? (y/n)
y
Do you want to continue? (y/n)
y
Enter one letter then press enter to get auto-complete suggestions (enter < to delete last character and . to stop) ...
e
e --> ef (3 predictions total)
f
ef --> ef (3 predictions total)
g
efg --> efg (2 predictions total)
h
efgh --> efgh (1 predictions total)
.
Do you want to continue? (y/n)
y
Enter one letter then press enter to get auto-complete suggestions (enter < to delete last character and . to stop) ...
x
x --> x (2 predictions total)
y
xy --> xyz (1 predictions total)
z
xyz --> xyz (1 predictions total)
.
Do you want to continue? (y/n)
n
```

```
java A2Test dict8.txt
Testing autocomplete:
Enter one letter then press enter to get auto-complete suggestions (enter < to delete last character and . to stop) ...
f
f --> f (798 predictions total)
u
fu --> fuchs (65 predictions total)
n
fun --> fun (14 predictions total)
n
funn --> funnel (2 predictions total)
y
funny --> funny (1 predictions total)
<
funn --> funnel (2 predictions total)
<
fun --> fun (14 predictions total)
d
fund --> fund (1 predictions total)
<
fun --> fun (14 predictions total)
<
fu --> fuchs (65 predictions total)
r
fur --> fur (18 predictions total)
n
furn --> furnace (2 predictions total)
a
furna --> furnace (1 predictions total)
<
furn --> furnace (2 predictions total)
<
fur --> fur (18 predictions total)
<
fu --> fuchs (65 predictions total)
<
f --> f (798 predictions total)
<
No predictions found for 
a
a --> a (1182 predictions total)
c
ac --> ac (73 predictions total)
t
act --> act (13 predictions total)
i
acti --> actinic (5 predictions total)
o
No predictions found for actio
<
acti --> actinic (5 predictions total)
v
activ --> activate (2 predictions total)
i
activi --> activism (1 predictions total)
t
No predictions found for activit
<
activi --> activism (1 predictions total)
<
activ --> activate (2 predictions total)
<
acti --> actinic (5 predictions total)
o
No predictions found for actio
n
No predictions found for action
.
Do you want to add action? (y/n)
y
Do you want to continue? (y/n)
y
Enter one letter then press enter to get auto-complete suggestions (enter < to delete last character and . to stop) ...
a
a --> a (1183 predictions total)
c
ac --> ac (74 predictions total)
t
act --> act (14 predictions total)
i
acti --> actinic (6 predictions total)
o
actio --> action (1 predictions total)
n
action --> action (1 predictions total)
.
Do you want to continue? (y/n)
n
```


Debugging is a skill that improves with practice. You have to spend time debugging your code using Java Debugging extensions in VS Code or using [JDB](https://canvas.pitt.edu/courses/164486/pages/debugging-java-programs-using-jdb). To help you in debugging, I have included a method, `printTree`, inside `AutoComplete.java` for printing the full tree or a subtree starting from a given prefix. Here is an example of printing the subtree starting from the prefix `fun`.

```java
printTree("fun");
```
The output would look like the following. The numbers between parentheses are the sizes and the asterisks mark nodes that have `isWord == true`.

```
==================== START: DLB Trie Starting from "fun" ====================
c (2)
 t (2)
  i (1)
   o (1)
    n * (1)
  o (1)
   r * (1)
d * (1)
e (2)
 r (2)
  a (1)
   l * (1)
  e (1)
   a (1)
    l * (1)
g (5)
 a (1)
  l * (1)
 i * (2)
  b (1)
   l (1)
    e * (1)
 o (1)
  i (1)
   d * (1)
 u (1)
  s * (1)
k * (1)
n (2)
 e (1)
  l * (1)
 y * (1)
==================== END: DLB Trie Starting from "fun" ====================
```
## Hints

- You may find the code of Lab 5 particularly useful for this assignment. You are free to use as much of that code as you want.
- Feel free to add and call private recursive helper methods if you need to.
- The `getNode` method already provided in `AutoComplete.java` can prove useful for the assignment.


## Writeup
Once you have completed your assignment, write a short paper (500-750 words) using [Github Markdown syntax](https://guides.github.com/features/mastering-markdown/) and named `a2.md` that summarizes your project in the following ways:
1.	Discuss how you solved the autocomplete problem in some detail. Include
    * how you set up the data structures necessary for the problem and
    * how your algorithms proceeded.  
    * Also indicate any coding or debugging issues you faced and how you resolved them.  If you were not able to get the program to work correctly, still include your approach and speculate as to what still needs to be corrected.
2.	Include an asymptotic analysis of the worst-case running time of the public methods of `AutoCompleteInterface` and comment on whether you were able to meet the running time indicated in the comments of these methods.

## Submission Requirements

You must submit your Github repository to GradeScope. We will only grade the following files:
1)	`AutoComplete.java`
3)	`a2.md`: A well written/formatted paper (see the Writeup section above for details on the paper)
4)	Assignment Information Sheet `infoSheet.md` (including compilation and execution information).

_The idea from your submission is that your TA (and/or the autograder if available) can compile and run your programs from the command line WITHOUT ANY additional files or changes, so be sure to test it thoroughly before submitting it. If the TA (and/or the autograder if available) cannot compile or run your submitted code it will be graded as if the program does not work.
If you cannot get the programs working as given, clearly indicate any changes you made and clearly indicate why on your Assignment Information Sheet.  You will lose some credit for not getting it to work properly but getting the main programs to work with modifications is better than not getting them to work at all.  A template for the Assignment Information Sheet can be found in this repository. You do not have to use this template, but your sheet should contain the same information.

_Note: If you use an IDE, such as NetBeans, Eclipse, or IntelliJ, to develop your programs, make sure the programs will compile and run on the command-line before submitting – this may require some modifications to your program (e.g., removing package information).

## Rubrics

__*Please note that if an autograder is available, its score will be used as a guidance for the TA, not as an official final score*__.

Please also note that the autograder rubrics are the definitive rubrics for the assignment. The rubrics below will be used by the TA to assign partial credit in case your code scored less than 40% of the autograder score. If your code is manually graded for partial credit, the maximum you can get for the autograded part is 60%.

Item|Points
----|------
`add(String word)` and `add()`|	20
`advance` | 10
`retreat` | 10
`reset`|	5
`isWord`|	5
`getNumberOfPredictions` |	5
`retrievePrediction` |	15
Efficiency| 10
Write-up paper|	10
Code style and documentation|	5
Assignment Information Sheet|	5
