package model.expressions;

import exceptions.ExpressionException;
import model.ADT.MyIDictionary;
import model.type.BoolType;
import model.type.IntType;
import model.value.BoolValue;
import model.value.IValue;
import model.value.IntValue;

public class RelationalExpression implements IExpression {
    private IExpression left;
    private RelationalOperation operation;
    private IExpression right;

    public RelationalExpression(IExpression left, RelationalOperation op, IExpression right) {
        this.left = left;
        this.operation = op;
        this.right = right;
    }


    @Override
    public IValue evaluate(MyIDictionary<String, IValue> symTbl) throws ExpressionException {
        if (!(this.left.evaluate(symTbl).getType().equals(new IntType())) || !(this.right.evaluate(symTbl).getType().equals(new IntType()))) {
            throw new ExpressionException("Wrong type of expression");
        }
        IntValue leftval = (IntValue) this.left.evaluate(symTbl);
        IntValue rightval = (IntValue) this.right.evaluate(symTbl);

        BoolValue eval = new BoolValue(false);
        switch(this.operation){
            case EQUAL -> {
                eval = new BoolValue(leftval.getValue() == rightval.getValue());
            }
            case LOWER -> {
                eval =  new BoolValue(leftval.getValue() < rightval.getValue());
            }
            case GREATER -> {
                eval = new BoolValue(leftval.getValue() > rightval.getValue());
            }
            case LOWEREQUAL -> {
                eval = new BoolValue(leftval.getValue() <= rightval.getValue());
            }
            case GREATEREQUAL ->
            {
                eval = new BoolValue(leftval.getValue() >= rightval.getValue());
            }
            case DIFFERENT -> {
                eval = new BoolValue(leftval.getValue() != rightval.getValue());
            }
        }
        return eval;
    }

    @Override
    public IExpression deepCopy() {
        return new RelationalExpression(this.left.deepCopy(), this.operation, this.right.deepCopy());
    }
}
