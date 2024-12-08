package model.state;

import exceptions.ControllerException;
import model.ADT.*;
import model.expressions.IExpression;
import model.statements.IStatement;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;

public class PrgState {
    private MyIStack<IStatement> execStack;
    private MyIDictionary<String, IValue> symTable;
    private MyIList<IValue> outputList;
    private IStatement originalStatement;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private IHeap heap;
    static int nextId = 0;
    private int id;

    public PrgState(IStatement initState, MyIStack<IStatement> execStack, MyIDictionary<String,
            IValue> symTable, MyIList<IValue> outputList,
                    MyIDictionary<StringValue, BufferedReader> fileTable,IHeap heap) {
        this.execStack = execStack;
        this.symTable = symTable;
        this.outputList = outputList;
        this.originalStatement = initState.deepCopy();
        this.fileTable = fileTable;
        this.heap = heap;
        this.id = this.getNextId();

        this.execStack.push(initState);
    }

    public MyIStack<IStatement> getExecStack() {
        return execStack;
    }

    public MyIDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public MyIList<IValue> getOutputList() {
        return outputList;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public IHeap getHeap(){
        return heap;
    }

    public Boolean isNotCompleted(){
        return (!this.getExecStack().isEmpty());
    }

    public synchronized int getNextId(){
        return nextId++;
    }

    public PrgState oneStep() throws ControllerException {
        try {
            MyIStack<IStatement> stack = this.getExecStack();
            IStatement currentstatement = stack.pop();
            return currentstatement.execute(this);
        }
        catch (Exception e) {
            throw new ControllerException(e.getMessage());
        }
    }

    public String toString(){
        return id + " - thread id\n" +
                execStack.toString() + "\n"
                + symTable.toString() + "\n"
                + outputList.toString() + "\n"
                + this.toStringFile() + "\n"
                + this.heap.toString() + "\n";
    }

    //do the same for symTable, execStack, outputList to neatly print them as below

    public String toStringFile(){
        String s = "File Table:\n";
        for (StringValue str : fileTable.getKeys()){
            s += str.getValue() + "\n";
        }
        return s;
    }
}
