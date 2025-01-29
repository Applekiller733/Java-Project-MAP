package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.MyIDictionary;
import model.ADT.MyIStack;
import model.expressions.RelationalExpression;
import model.expressions.RelationalOperation;
import model.expressions.VariableExpression;
import model.state.PrgState;
import model.type.IType;
import model.expressions.IExpression;
import model.type.IntType;

public class ForStatement implements IStatement{
    private IStatement statement1;
    private String str;
    private IExpression exp1;
    private IExpression exp2;
    private IExpression exp3;

    public ForStatement(IStatement statement1, String str, IExpression exp1, IExpression exp2, IExpression exp3){
        this.statement1 = statement1;
        this.str = str;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        MyIStack<IStatement> stack = state.getExecStack();
        IStatement fortoWhile = new CompStatement(new AssignStatement(str, exp1),
                new WhileStatement(new RelationalExpression(new VariableExpression(str), RelationalOperation.LOWER, exp2),
                        new CompStatement(statement1, new AssignStatement(str, exp3))));
        stack.push(fortoWhile);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new ForStatement(statement1.deepCopy(), str, exp1, exp2, exp3);
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException {
        IType type1 = exp1.typecheck(typeEnv);
        IType type2 = exp2.typecheck(typeEnv);
        IType type3 = exp3.typecheck(typeEnv);
        if (type1.equals(new IntType()) && type2.equals(new IntType()) && type3.equals(new IntType())) {
            return typeEnv;
        }
        else
            throw new ExpressionException("FORSTMT: Type mismatch");
    }

    @Override
    public String toString(){
        return "for(" + str + "=" + exp1 + "; " + str + "<" + exp2 + "; " + str + "=" + exp3 + ") " +
                "{" + statement1.toString() + "}";
    }
}
