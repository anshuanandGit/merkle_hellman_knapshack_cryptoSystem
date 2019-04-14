/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merkle.hellman.knapsack.crypto.project;

import java.math.BigInteger;
import java.util.Iterator;

/**
 * This class will hold single linked list using Miachel Mann's ObjectNode class
 *
 *
 * @author Anshu Anand
 */
public class SinglyLinkedList {

    // this field helds reference to header of list
    private ObjectNode head;
    // this field helds reference to tail of list
    private ObjectNode tail;
    // this field helds reference to current node of list
    private ObjectNode current;
    // this field helds length of list
    private int countNodes = 0;

    /**
     * This is the constructor for single linked list.
     */
    public SinglyLinkedList() {
        head = null;
        tail = null;
        countNodes = 0;
    }

    /**
     * This method adds a node at the end of linked list
     *
     *
     * @comment In the worst case scenario and best scenario function will run 1
     * This routine is Big Theeta(1)
     * @postcondition one node must be added as end note to the linked list
     * @param val
     */
    public void addAtEndNode(BigInteger val) {

        ObjectNode newNode = new ObjectNode(val, null);
        countNodes++;
        if (head == null) {
            head = newNode;
            tail = head;

        } else {
            tail.setLink(newNode);
            tail = newNode;
        }
    }

    /**
     * This method adds a node at the head of linked list
     *
     *
     * @comment In the worst case scenario and best scenario function will run 1
     * This routine is Big Theeta(1)
     * @postcondition a node must be added at the header of the linked list
     * @param val
     */
    public void addAtFrontNode(BigInteger val) {

        ObjectNode newNode = new ObjectNode(val, null);
        countNodes++;

        if (head == null) {
            head = newNode;
            tail = head;
        } else {
            newNode.setLink(head);
            head = newNode;
        }
    }

    /**
     * This method returns the length of linked list.
     *
     *
     * @comment In the worst case scenario and best scenario function will run 1
     * This routine is Big Theeta(1)
     * @precondition numbers of the node on the linked list must be non null
     * @return
     */
    public int CountNodes() {
        return countNodes;
    }

    /**
     * This method returns the last node of the linked list.
     *
     *
     * @comment In the worst case scenario and best scenario function will run 1
     * This routine is Big Theeta(1)
     * @precondition numbers of the node on the linked list must be non null
     * @return
     */
    public Object getLast() {
        return tail.getData();
    }

    /**
     * This method returns the node from a specific position .
     *
     *
     * @comment In the worst case scenario and best scenario function will run 1
     * This routine is Big Theeta(1)
     * @precondition numbers of the node on the linked list must be non null
     * @param indx
     * @return
     */
    public ObjectNode getObjectAt(int indx) {
        if (indx > countNodes) {
            throw new IllegalArgumentException("The index [" + indx + "] is greater than the current size [" + countNodes + "].");
        }
        ObjectNode current = head.listPosition(head, indx);
        return current;
    }

    /**
     * This method gets head in the linked list
     *
     *
     * @comment In the worst case scenario and best scenario function will run 1
     * This routine is Big Theeta(1)
     * @precondition numbers of the node on the linked list must be non null
     * @return
     */
    public ObjectNode getHead() {
        return head;
    }

    /**
     * This method returns tail element in the list
     *
     *
     * @comment In the worst case scenario and best scenario function will run 1
     * This routine is Big Theeta(1)
     * @precondition numbers of the node on the linked list must be non null
     * @return
     */
    public ObjectNode getTail() {
        return tail;
    }

    /**
     * This method checks if the iterator in list has next element
     *
     *
     * @comment In the worst case scenario and best scenario function will run 1
     * This routine is Big Theeta(1)
     * @precondition numbers of the node on the linked list must be non null
     * @return
     */
    public boolean hasNext() {
        boolean exist = false;
        if (current != null) {
            exist = true;
        }
        return exist;
    }

    /**
     * This method returns next element in the list
     *
     *
     * @comment In the worst case scenario and best scenario function will run 1
     * This routine is Big Theeta(1)
     * @precondition numbers of the node on the linked list must be non null
     * @return
     */
    public Object next() {

        Object xx = current.getData();
        current = current.listPosition(current, 1);
        return xx;
    }

    /**
     * This method resets the cursor to header of the linked list.
     *
     *
     * @comment In the worst case scenario and best scenario function will run 1
     * This routine is Big Theeta(1)
     */
    public void reset() {
        current = head.listPosition(head, 0);
    }

    /**
     * This override method returns each node for the Singley Linked list.
     *
     *
     * @comment In the worst case scenario and best scenario function will run N
     * This routine is Big Theeta(N)
     * @precondition numbers of the node on the linked list must be non null
     * @return
     */
    @Override
    public String toString() {
        String list = "";
        list += "[" + this.head.getData() + "]";

        ObjectNode curr = head.getLink();
        while (curr != null) {
            list += "[" + curr.getData().toString() + "]";
            curr = curr.getLink();
        }

        return list;
    }

}
