package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.MyIDictionary;
import model.ADT.MyILatchTable;
import model.ADT.MyIList;
import model.ADT.MyIStack;
import model.state.PrgState;
import model.type.IType;
import model.type.IntType;
import model.value.IValue;
import model.value.IntValue;

public class AwaitStatement implements IStatement{
    private String varname;

    public AwaitStatement(String varname){
        this.varname = varname;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        MyIStack<IStatement> stack = state.getExecStack();
        IValue foundIndex = state.getSymTable().get(varname);
        if (foundIndex == null || !foundIndex.getType().equals(new IntType())) {
            throw new ExpressionException("AWAITSTMT:Variable '" + varname + "' not found in SymTable");
        }
        IntValue index = (IntValue) foundIndex;
        MyILatchTable latchTable = state.getLatchTable();
        if (!latchTable.containsKey(index.getValue())){
            throw new StatementException("AWAITSTMT:Variable '" + varname + "' not in latch table");
        }
        else if (latchTable.get(index.getValue()) != 0){
            stack.push(new AwaitStatement(varname));
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new AwaitStatement(varname);
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException {
        IType typevar = typeEnv.get(varname);
        if (!typevar.equals(new IntType())){
            throw new StatementException("AWAITSTMT: Type mismatch");
        }
        return typeEnv;
    }

    @Override
    public String toString(){
        return "await(" + varname + ")";
    }
}
