package model.expressions;

import exceptions.ExpressionException;
import model.ADT.IHeap;
import model.ADT.MyIDictionary;
import model.type.IType;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

public class ReadHeapExpression implements IExpression {
    private IExpression exp;

    public ReadHeapExpression(IExpression exp) {
        this.exp = exp;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> symTbl, IHeap heap) throws ExpressionException {
        IValue val = exp.evaluate(symTbl, heap);
        if (!(val instanceof RefValue)){
            throw new ExpressionException("rHEAPEXP:Wrong type of expression");
        }
        int addr = ((RefValue)val).getAddress();
        if (!heap.exists(addr)){
            throw new ExpressionException("rHEAPEXP:Address " + addr + " not found in heap");
        }
        return heap.get(addr);
    }

    @Override
    public IExpression deepCopy() {
        return new ReadHeapExpression(exp.deepCopy());
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException {
        IType type;
        type = exp.typecheck(typeEnv);
        if (type instanceof RefType){
            RefType reft = (RefType)type;
            return reft.getInner();
        }
        else
            throw new ExpressionException("rHEAPEXP:Argument not of ref type!");
    }

    @Override
    public String toString() {
        return "rH(" + exp + ")";
    }
}
