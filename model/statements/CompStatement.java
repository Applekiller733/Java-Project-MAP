package model.statements;

import model.ADT.MyIStack;
import model.state.PrgState;

public class CompStatement implements IStatement{
    private IStatement first;
    private IStatement second;

    public CompStatement(IStatement first, IStatement second) {
        this.first = first;
        this.second = second;
    }

    public String toString(){
        return "("+first.toString() + ";" + second.toString()+")";
    }

    public PrgState execute(PrgState state) {
        state.getExecStack().push(this.second);
        state.getExecStack().push(this.first);
        return state;
    }

    public IStatement deepCopy(){
        return new CompStatement(first.deepCopy(), second.deepCopy());
    }
}
