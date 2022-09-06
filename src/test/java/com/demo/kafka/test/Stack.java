package com.demo.kafka.test;

public class Stack {

    private int arr[];
    private int top;
    private int capacity;

    public Stack(int size) {
        arr = new int[size];
        capacity = size;
        top = -1;
    }

    public void push(int x) {
        if (isFull()) {
            System.err.println("Stack OverFlow");
            System.exit(1);
        }

        // insert element on top of stack
        System.out.println("Inserting " + x);
        arr[++top] = x;
    }

    public int pop() {

        if (isEmpty()) {
            System.err.println("STACK EMPTY");
            System.exit(1);
        }

        // pop element from top of stack
        return arr[top--];
    }

    public int getSize() {
        return top + 1;
    }

    public Boolean isEmpty() {
        return top == -1;
    }

    public Boolean isFull() {
        return top == capacity - 1;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i <= top; i++) {
            s.append(arr[i]).append(", ");
        }
        s.delete(s.length() - 2, s.length() - 1);
        return s.toString();
    }

    public static void main(String[] args) {
        Stack stack = new Stack(5);

        stack.push(1);
        stack.push(2);
        stack.push(3);

        System.out.println("Stack: " + stack);

        int poppedElement;
        poppedElement = stack.pop();
        System.out.println("After popping out " + poppedElement);
        System.out.println("Stack: " + stack);

        poppedElement = stack.pop();
        System.out.println("After popping out " + poppedElement);
        System.out.println("Stack: " + stack);
    }
}
