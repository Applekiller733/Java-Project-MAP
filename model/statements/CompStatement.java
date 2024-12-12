package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.MyIDictionary;
import model.ADT.MyIStack;
import model.state.PrgState;
import model.type.IType;

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
        return null;
    }

    public IStatement deepCopy(){
        return new CompStatement(first.deepCopy(), second.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException
    {
        return second.typecheck(first.typecheck(typeEnv));
    }
}
