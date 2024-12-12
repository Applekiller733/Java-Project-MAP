package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.MyIDictionary;
import model.state.PrgState;
import model.type.IType;
import model.type.RefType;
import model.value.IValue;

import model.expressions.IExpression;
import model.value.RefValue;

public class HeapAllocationStatement implements IStatement{

    private String variableName;
    private IExpression expression;

    public HeapAllocationStatement(String variableName, IExpression expression){
        this.variableName = variableName;
        this.expression = expression;
    }

//    @Override
//    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
//        var symTable= state.getSymTable();
//        var heap = state.getHeap();
//        if(!symTable.contains(variableName)){
//            throw new StatementException("There is no variable "+ variableName +" in the symTable");
//
//        }
//        IValue variableValue = symTable.get(variableName);
//        if(!(variableValue.getType() instanceof RefType)){
//            throw new StatementException("The variable is not of RefType");
//        }
//
//        IValue value =expression.evaluate(symTable,heap);
//        if(!value.getType().equals(((RefType)variableValue.getType()).getInner())){
//            throw new StatementException("The expression is not of RefType");
//        }
//
//        int address= heap.allocate(value);
//        symTable.insert(variableName, new RefValue(address,value.getType()));
//        return state;
//
//    }



    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {

        var symTable = state.getSymTable();
        var heap = state.getHeap();
        if(!symTable.contains(variableName)){
            throw new StatementException("HEAPALLOCSTMT:There is no variable " + variableName + " in the sym table");
        }

        IValue variableValue = symTable.get(this.variableName);

        if(!(variableValue.getType() instanceof RefType)) {
            throw new StatementException("HEAPALLOCSTMT:Variable is not of Ref type");
        }

        IValue value = expression.evaluate(symTable, heap);

        if(!value.getType().equals(((RefType)variableValue.getType()).getInner())){
            throw new StatementException("HEAPALLOCSTMT:The expression is a different type of the referenced type");
        }

        int address = heap.allocate(value);
//        System.out.println("Allocated value:" + value.toString() + " to address:" + address);
        symTable.insert(variableName,new RefValue(address, value.getType()));
//        System.out.println("After symtable insert");
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new HeapAllocationStatement(this.variableName,this.expression);
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException {
        IType typevar = typeEnv.get(this.variableName);
        IType typeexp = expression.typecheck(typeEnv);
        if (typevar.equals(new RefType(typeexp))){
            return typeEnv;
        }
        else
            throw new StatementException("HEAPALLOCSTMT: Left hand side and right hand side not of matching type!");
    }

    @Override
    public String toString(){
        return "new(" + this.variableName + "," + this.expression + ")";
    }
}
