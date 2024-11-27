package model.expressions;

import exceptions.ExpressionException;
import model.ADT.IHeap;
import model.ADT.MyIDictionary;
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
    public String toString() {
        return "rH(" + exp + ")";
    }
}
