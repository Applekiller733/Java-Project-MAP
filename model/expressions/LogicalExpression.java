package model.expressions;
import exceptions.ExpressionException;
import model.ADT.IHeap;
import model.ADT.MyIDictionary;
import model.expressions.LogicalOperation;
import model.type.BoolType;
import model.type.IType;
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

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException {
        IType type1, type2;
        type1 = left.typecheck(typeEnv);
        type2 = right.typecheck(typeEnv);
        if (type1.equals(new BoolType())){
            if (type2.equals(new BoolType())){
                return new BoolType();
            }
            else
                throw new ExpressionException("LOGICALEXP:Second operand is not boolean!\n");
        }
        else
            throw new ExpressionException("LOGICALEXP:First operand is not boolean!\n");
    }
}
