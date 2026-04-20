package com.devmind.controller.threading;

public class MyLinkedList {
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
            this.next = null;
        }
        
        public ListNode(int val,ListNode node) {
            this.val = val;
            this.next = node;
        }
    }

    private ListNode head;

    public void addFirst(int val) {
        ListNode newNode = new ListNode(val);
        newNode.next = head;
        head = newNode;
    }

    public void addLast(int val) {
        ListNode newNode = new ListNode(val);

        if (head == null) {
            head = newNode;
            return;
        }

        ListNode temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }

        temp.next = newNode;
    }

    public void addAtIndex(int index, int val) {

        if (index == 0) {
            addFirst(val);
            return;
        }

        ListNode newNode = new ListNode(val);
        ListNode temp = head;

        for (int i = 0; i < index - 1; i++) {
            if (temp == null) return; // index out of bounds
            temp = temp.next;
        }

        if (temp == null) return;

        newNode.next = temp.next;
        temp.next = newNode;
    }

    public void printList() {
        ListNode temp = head;

        while (temp != null) {
            System.out.print(temp.val + " -> ");
            temp = temp.next;
        }

        System.out.println("null");
    }
    
    public static void main(String[] args) {

        MyLinkedList list = new MyLinkedList();

        list.addFirst(5);
        list.addFirst(4);
        list.addLast(3);
        list.addLast(1);

        list.printList();
        
        list.head = rec(2,3,list.head);
        list.printList();
    }
    
    public static ListNode rec(int val,int index,ListNode node) {
    	return  insertRec(val,index,node);
    }

    public static ListNode insertRec(int val, int index, ListNode node) {
        if (index == 0) {
            return new ListNode(val, node);
        }

        if (node == null) return null;

        node.next = insertRec(val, index - 1, node.next);
        return node;
    }
}
