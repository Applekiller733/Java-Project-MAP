package model.expressions;
import exceptions.ExpressionException;
import model.ADT.IHeap;
import model.ADT.MyIDictionary;
import model.expressions.LogicalOperation;
import model.type.BoolType;
import model.value.BoolValue;
import model.value.IValue;

public class LogicalExpression implements IExpression {
    private IExpression left;
    private IExpression right;
    private LogicalOperation operation;

    public LogicalExpression(IExpression left, LogicalOperation operation, IExpression right) {
        this.left = left;
        this.operation = operation;
        this.right = right;
    }
    @Override
    public String toString(){
        return left.toString() + " " + operation.toString().toLowerCase() + " " + right.toString();
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> symTbl, IHeap heap) throws ExpressionException {
        IValue left = this.left.evaluate(symTbl, heap);
        IValue right = this.right.evaluate(symTbl, heap);
        if (!(left.getType().equals(new BoolType()) && right.getType().equals(new BoolType()))) {
            throw new ExpressionException("LOGICALEXP:One of the values is not boolean!\n");
        }
        Boolean leftvalue = ((BoolValue)left).getValue();
        Boolean rightvalue = ((BoolValue)right).getValue();
        if (this.operation == LogicalOperation.AND) {
            return new BoolValue(leftvalue && rightvalue);
        }
        else {
            return new BoolValue(leftvalue || rightvalue);
        }
    }

    public IExpression deepCopy(){
        return new LogicalExpression(this.left.deepCopy(), this.operation, this.right.deepCopy());
    }
}
