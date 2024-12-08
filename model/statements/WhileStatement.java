package model.statements;

import exceptions.EmptyStackException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.state.PrgState;
import model.expressions.IExpression;
import model.type.BoolType;
import model.value.BoolValue;
import model.value.IValue;

public class WhileStatement implements IStatement {
    private IExpression exp;
    private IStatement body;

    public WhileStatement(IExpression exp, IStatement body) {
        this.exp = exp;
        this.body = body;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        IValue val = this.exp.evaluate(state.getSymTable(), state.getHeap());
        if (!val.getType().equals(new BoolType())){
            throw new ExpressionException("WHILESTMT:While statement expects a boolean value");
        }
        BoolValue boolVal = (BoolValue) val;
        if (boolVal.getValue()){
            state.getExecStack().push(this);
            state.getExecStack().push(this.body);
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(this.exp.deepCopy(), this.body.deepCopy());
    }

    @Override
    public String toString(){
        return "while(" + this.exp.toString() + ", " + this.body.toString() + ")";
    }
}
