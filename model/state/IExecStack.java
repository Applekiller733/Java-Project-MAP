package model.state;

import exceptions.EmptyStackException;
import model.statements.IStatement;

public interface IExecStack {

    void push(IStatement statement);
    IStatement pop() throws EmptyStackException;
    int size();
    boolean isEmpty();

}
