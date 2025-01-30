package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.MyIDictionary;
import model.state.PrgState;
import model.expressions.IExpression;
import model.type.IType;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

public class WriteHeapStatement implements IStatement {
    private String varname;
    private IExpression expression;

    public WriteHeapStatement(String varname, IExpression expression) {
        this.varname = varname;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        if(!state.getSymTable().contains(varname)){
            throw new ExpressionException("wHEAPSTMT:Variable does not exist");
        }
        RefValue val = (RefValue) state.getSymTable().get(varname);
        if(!(val.getType() instanceof RefType)){
            throw new ExpressionException("wHEAPSTMT:Variable not of Reftype");
        }
        if (!state.getHeap().exists(val.getAddress())){
            throw new ExpressionException("wHEAPSTMT:Address does not exist in heap");
        }

        IValue expeval = this.expression.evaluate(state.getSymTable(), state.getHeap());
        if (!expeval.getType().equals((val.getLocationType()))){
            throw new ExpressionException("wHEAPSTMT:Type mismatch");
        }
        state.getHeap().set(val.getAddress(), expeval);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException {
        IType typevar = typeEnv.get(varname);
        IType typeexp = expression.typecheck(typeEnv);
        if (typevar.equals(new RefType(typeexp))){
            return typeEnv;
        }
        else
            throw new StatementException("wHEAPSTMT:Type mismatch");
    }

    @Override
    public String toString(){
        return "wH(" + varname + "," + expression + ")";
    }
}
