package model.state;

import exceptions.EmptyStackException;
import model.ADT.MyStack;
import model.statements.IStatement;

public class ExecStack implements IExecStack{
    private MyStack<IStatement> stack;
    public ExecStack() {
        stack = new MyStack<>();
    }

    @Override
    public void push(IStatement statement) {
        this.stack.push(statement);
    }

    @Override
    public IStatement pop() throws EmptyStackException {
        if ( this.stack.isEmpty() ) {
            throw new EmptyStackException("Stack is empty");
        }
        else
            return this.stack.pop();
    }

    @Override
    public int size() {
        return this.stack.size();
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    public String toString(){
        return super.toString();
    }

    public MyStack<IStatement> getStack() {
        return this.stack;
    }
}
