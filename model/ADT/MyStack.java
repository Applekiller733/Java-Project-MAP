package model.ADT;

import exceptions.EmptyStackException;

import java.util.Stack;

public class MyStack<T> implements MyIStack<T>{
    Stack<T> stack;

    public MyStack(){
        stack = new Stack<>();
    }

    @Override
    public T pop() throws EmptyStackException {
        if (this.isEmpty()){
            throw new EmptyStackException("Stack is empty!");
        }
        return this.stack.pop();
    }

    @Override
    public void push(T element) {
        this.stack.push(element);
    }

    @Override
    public boolean isEmpty() {
        if (this.size() == 0)
            return true;
        return false;
    }

    @Override
    public int size() {
        return this.stack.size();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for ( T el : this.stack ){
            str.append(el);
            str.append("\n");
        }

        return "The stack contains:" + str.toString();
    }

    @Override
    public Stack<T> getStack() {
        return this.stack;
    }
}
