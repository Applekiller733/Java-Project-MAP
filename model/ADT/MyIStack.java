package model.ADT;

import exceptions.EmptyStackException;

import java.util.Stack;

public interface MyIStack<T> {
    T pop() throws EmptyStackException;
    void push(T element);
    boolean isEmpty();
    int size();
    String toString();
    public Stack<T> getStack();
}
