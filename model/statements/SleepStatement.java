package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.MyIDictionary;
import model.ADT.MyIStack;
import model.state.PrgState;
import model.type.IType;

public class SleepStatement implements IStatement{
    private int num;

    public SleepStatement(int num) {
        this.num = num;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        if (num != 0){
            MyIStack<IStatement> stack = state.getExecStack();
            stack.push(new SleepStatement(num - 1));
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new SleepStatement(this.num);
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException {
        return typeEnv;
    }

    @Override
    public String toString(){
        return "sleep(" + num + ")";
    }
}
