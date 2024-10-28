package model.state;

import model.ADT.*;
import model.expressions.IExpression;
import model.statements.IStatement;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;

public class PrgState {
    private MyIStack<IStatement> execStack;
    private MyIDictionary<String, IValue> symTable;
    private MyIList<String> outputList;
    private IStatement originalStatement;
    private MyIDictionary<StringValue, BufferedReader> fileTable;

    public PrgState(IStatement initState, MyIStack<IStatement> execStack, MyIDictionary<String,
            IValue> symTable, MyIList<String> outputList,
                    MyIDictionary<StringValue, BufferedReader> fileTable) {
        this.execStack = execStack;
        this.symTable = symTable;
        this.outputList = outputList;
        this.originalStatement = initState.deepCopy();
        this.fileTable = fileTable;

        this.execStack.push(initState);
    }

    public MyIStack<IStatement> getExecStack() {
        return execStack;
    }

    public MyIDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public MyIList<String> getOutputList() {
        return outputList;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public String toString(){
        return execStack.toString() + "\n"
                + symTable.toString() + "\n"
                + outputList.toString() + "\n"
                + this.toStringFile() + "\n";
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
