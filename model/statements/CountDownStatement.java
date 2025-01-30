package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.MyIDictionary;
import model.ADT.MyILatchTable;
import model.ADT.MyIList;
import model.state.PrgState;
import model.type.IType;
import model.type.IntType;
import model.value.IValue;
import model.value.IntValue;

public class CountDownStatement implements IStatement{
    public String varname;

    public CountDownStatement(String varname){
        this.varname = varname;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        if (!symTable.contains(varname) || !symTable.get(varname).getType().equals(new IntType())) {
            throw new StatementException("COUNTDOWNSTMT: variable not contained in symtable or not of int type");
        }
        int foundIndex = ((IntValue) symTable.get(varname)).getValue();
        MyILatchTable latchTable = state.getLatchTable();
        if (!latchTable.containsKey(foundIndex)) {
            throw new StatementException("COUNTDOWNSTMT: variable not contained in latch table");
        }
        else if (latchTable.get(foundIndex) > 0){
            latchTable.put(foundIndex, latchTable.get(foundIndex) - 1);
        }
        state.getOutputList().add(new IntValue(state.getId()));
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CountDownStatement(varname);
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException {
        IType typevar = typeEnv.get(varname);
        if (typevar != null && !typevar.equals(new IntType())){
            throw new StatementException("COUNTDOWNSTMT: variable not of int type");
        }
        return typeEnv;
    }

    @Override
    public String toString(){
        return "countDown(" + varname + ")";
    }
}
