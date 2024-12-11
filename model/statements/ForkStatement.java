package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.*;
import model.state.PrgState;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;

public class ForkStatement implements IStatement{
    IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }
    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {


        MyIStack<IStatement> newExecStack = new MyStack<>();

        MyIDictionary<String, IValue> newSymTable = state.getSymTable().deepCopy(); //todo check if correct

        MyIList<IValue> newOutput = state.getOutputList();

        MyIDictionary<StringValue, BufferedReader> newFileTable = state.getFileTable();

        IHeap newHeap = state.getHeap();

        PrgState newthread = new PrgState(statement, newExecStack, newSymTable, newOutput, newFileTable, newHeap);
        return newthread;
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ")";
    }

    @Override
    public IStatement deepCopy() {
        return new ForkStatement(statement.deepCopy());
    }
}
