package model.expressions;

import exceptions.ExpressionException;
import model.ADT.IHeap;
import model.ADT.MyIDictionary;
import model.type.BoolType;
import model.type.IType;
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
    public IValue evaluate(MyIDictionary<String, IValue> symTbl, IHeap heap) throws ExpressionException {
        if (!(this.left.evaluate(symTbl, heap).getType().equals(new IntType())) || !(this.right.evaluate(symTbl, heap).getType().equals(new IntType()))) {
            throw new ExpressionException("RELATIONEXP:Wrong type of expression");
        }
        IntValue leftval = (IntValue) this.left.evaluate(symTbl, heap);
        IntValue rightval = (IntValue) this.right.evaluate(symTbl, heap);

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

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException {
        IType type1, type2;
        type1 = this.left.typecheck(typeEnv);
        type2 = this.right.typecheck(typeEnv);
        if (type1.equals(new IntType())){
            if(type2.equals(new IntType())){
                return new BoolType();
            }
            else
                throw new ExpressionException("RELATIONEXP:Second operand not int type!");
        }
        else
            throw new ExpressionException("RELATIONEXP:First operand not int type!");
    }

    @Override
    public String toString(){
        return this.left.toString() + " " + this.operation + " " + this.right.toString();
    }
}
