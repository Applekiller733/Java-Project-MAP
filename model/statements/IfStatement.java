package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.type.BoolType;
import model.type.IType;
import model.value.BoolValue;
import model.value.IValue;

import java.sql.Statement;

public class IfStatement implements IStatement {
    IExpression expression;
    private IStatement thenStatement;
    private IStatement elseStatement;

    public IfStatement(IExpression expression, IStatement thenStatement, IStatement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    public String toString(){
        return "IF("+ expression.toString() +") THEN{"+ thenStatement.toString() +
                "} ELSE{"+ elseStatement.toString()+"}";
    }

    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        IValue val = this.expression.evaluate(state.getSymTable(), state.getHeap());
        if (!val.getType().equals(new BoolType())) {
            throw new StatementException("IFSTMT:The expression is not a bool type!\n");
        }
        if (((BoolValue) val).getValue()) {
            state.getExecStack().push(this.thenStatement);
        } else
            state.getExecStack().push(this.elseStatement);
        return null;
    }

    public IStatement deepCopy(){
        return new IfStatement(expression.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException {
        IType typeexp = expression.typecheck(typeEnv);
        if (typeexp.equals(new BoolType())){
            thenStatement.typecheck(typeEnv);
            elseStatement.typecheck(typeEnv);
            return typeEnv;
        }
        else
            throw new StatementException("IFSTMT:The expression is not a bool type!\n");
    }
}
