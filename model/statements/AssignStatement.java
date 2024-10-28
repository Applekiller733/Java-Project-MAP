package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.state.PrgState;
import model.expressions.IExpression;
import model.value.IValue;

public class AssignStatement implements IStatement{
    String id;
    IExpression expression;

    public AssignStatement(String id, IExpression expression){
        this.id = id;
        this.expression = expression;
    }

    public String toString(){
        return this.id + "=" + this.expression;
    }
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        if (!state.getSymTable().contains(id)){
            throw new StatementException("Variable wasn't declared previously!\n");
        }
        IValue val = expression.evaluate(state.getSymTable());
        if (!val.getType().equals(state.getSymTable().get(id).getType())){
            throw new StatementException("Type mismatch!\n");
        }
        state.getSymTable().insert(id, val);
        return state;
    }

    public IStatement deepCopy(){
        return new AssignStatement(this.id, this.expression.deepCopy());
    }
}
