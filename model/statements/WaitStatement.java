package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.MyIDictionary;
import model.ADT.MyIStack;
import model.expressions.ValueExpression;
import model.state.PrgState;
import model.type.IType;
import model.value.IntValue;

public class WaitStatement implements IStatement {
    private int num;

    public WaitStatement(int num) {
        this.num = num;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        if (num != 0){
            MyIStack<IStatement> stack = state.getExecStack();
            stack.push(new CompStatement(new PrintStatement(new ValueExpression(new IntValue(num))), new WaitStatement(num - 1)));
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new WaitStatement(num);
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException {
        return typeEnv;
    }

    @Override
    public String toString(){
        return "wait(" + num + ")";
    }
}
