package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.MyIDictionary;
import model.ADT.MyIStack;
import model.expressions.RelationalExpression;
import model.expressions.RelationalOperation;
import model.state.PrgState;
import model.type.IType;
import model.expressions.IExpression;

public class SwitchStatement implements IStatement{
    private IExpression condexpression;
    private IExpression exp1;
    private IStatement statement1;
    private IExpression exp2;
    private IStatement statement2;
    private IStatement statement3;

    public SwitchStatement(IExpression condexpression, IExpression exp1, IStatement statement1,
                           IExpression exp2, IStatement statement2, IStatement statement3) {
        this.condexpression = condexpression;
        this.exp1 = exp1;
        this.statement1 = statement1;
        this.exp2 = exp2;
        this.statement2 = statement2;
        this.statement3 = statement3;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        MyIStack<IStatement> stack = state.getExecStack();
        IStatement converted = new IfStatement(
                new RelationalExpression(condexpression, RelationalOperation.EQUAL ,exp1), statement1,
                new IfStatement(new RelationalExpression(condexpression, RelationalOperation.EQUAL, exp2), statement2,
                        statement3));
        stack.push(converted);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new SwitchStatement(condexpression, exp1, statement1, exp2, statement2, statement3);
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException {
        IType type = condexpression.typecheck(typeEnv);
        IType type1 = exp1.typecheck(typeEnv);
        IType type2 = exp2.typecheck(typeEnv);
        if (type.equals(type1) && type1.equals(type2)) {
            statement1.typecheck(typeEnv);
            statement2.typecheck(typeEnv);
            statement3.typecheck(typeEnv);
            return typeEnv;
        }
        else
            throw new ExpressionException("SWITCHSTMT:Type mismatch");
    }

    @Override
    public String toString(){
        return "switch(" + condexpression + ") (case " + exp1 + "," + statement1 + ") (case " + exp2 + "," + statement2 + ")";
    }
}
