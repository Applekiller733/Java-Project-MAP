package model.ADT;

import exceptions.EmptyStackException;

public interface MyIStack<T> {
    T pop() throws EmptyStackException;
    void push(T element);
    boolean isEmpty();
    int size();
    String toString();
}
