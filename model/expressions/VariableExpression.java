package model.expressions;

import exceptions.ExpressionException;
import model.ADT.IHeap;
import model.ADT.MyIDictionary;
import model.type.IType;
import model.value.IValue;

public class VariableExpression implements IExpression{
    private String variable;

    public VariableExpression(String variable) {
        this.variable = variable;

    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> symTbl, IHeap heap) throws ExpressionException {
        return symTbl.get(this.variable);
    }

    @Override
    public String toString(){
        return variable;
    }

    public IExpression deepCopy(){
        return new VariableExpression(variable);
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException {
        return typeEnv.get(this.variable);
    }
}
