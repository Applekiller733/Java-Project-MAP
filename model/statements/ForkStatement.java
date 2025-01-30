package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.*;
import model.state.PrgState;
import model.type.IType;
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

        MyILatchTable newLatchTable = state.getLatchTable();

        PrgState newthread = new PrgState(statement, newExecStack, newSymTable, newOutput, newFileTable, newHeap, newLatchTable);
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

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException {
        statement.typecheck(typeEnv);
        return typeEnv;
    }
}
