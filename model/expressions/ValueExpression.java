package model.expressions;

import model.ADT.IHeap;
import model.ADT.MyIDictionary;
import model.state.PrgState;
import model.value.IValue;

public class ValueExpression implements IExpression{
    private IValue value;

    public ValueExpression(IValue value) {
        this.value = value;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> symTbl, IHeap heap) {
        return value;
    }

    @Override
    public String toString(){
        return value.toString();
    }

    public IExpression deepCopy(){
        return new ValueExpression(value);
    }
}
