package model.statements;

import exceptions.EmptyStackException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.MyIDictionary;
import model.state.PrgState;
import model.expressions.IExpression;
import model.type.BoolType;
import model.type.IType;
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
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException {
        IType typeexp = exp.typecheck(typeEnv);
        if (typeexp.equals(new BoolType())){
            body.typecheck(typeEnv);
            return typeEnv;
        }
        else
            throw new StatementException("WHILESTMT:While statement expects a boolean value");
    }

    @Override
    public String toString(){
        return "while(" + this.exp.toString() + ", " + this.body.toString() + ")";
    }
}
