package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.IHeap;
import model.ADT.MyIDictionary;
import model.ADT.MyILatchTable;
import model.expressions.VariableExpression;
import model.state.PrgState;
import model.type.IType;
import model.expressions.IExpression;
import model.type.IntType;
import model.value.IValue;
import model.value.IntValue;

public class NewLatchStatement implements IStatement{
    private String varname;
    private IExpression exp;

    public NewLatchStatement(String varname, IExpression exp) {
        this.varname = varname;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        IHeap heap = state.getHeap();
        IValue num1 = this.exp.evaluate(symTable, heap);
        if (!num1.getType().equals(new IntType())){
            throw new ExpressionException("NEWLATCHSTMT:Wrong type of expression");
        }
        if (symTable.get(varname) == null){
            throw new ExpressionException("NEWLATCHSTMT:Variable " + varname + " not found");
        }
        IntValue val1 = (IntValue) num1;
        MyILatchTable latchTable = state.getLatchTable();
        int newfreeloc = latchTable.getFree();
        latchTable.put(newfreeloc, val1.getValue());
        symTable.put(varname, new IntValue(newfreeloc));

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NewLatchStatement(varname, exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException {
        IType typevar = typeEnv.get(varname);
        IType typeexp = exp.typecheck(typeEnv);
        if (!typevar.equals(new IntType()) || !typeexp.equals(new IntType())) {
            throw new StatementException("NEWLATCHSTMT: Type mismatch");
        }
        return typeEnv;
    }

    @Override
    public String toString(){
        return "newLatch(" + varname + "," + exp + ")";
    }
}
