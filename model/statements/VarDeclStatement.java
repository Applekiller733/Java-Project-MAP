package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.MyIDictionary;
import model.state.PrgState;
import model.type.IType;

import javax.swing.plaf.synth.SynthTextAreaUI;

public class VarDeclStatement implements IStatement{
    private String varName;
    IType type;

    public VarDeclStatement(String varName, IType type){
        this.varName = varName;
        this.type = type;
    }

    public PrgState execute(PrgState state) throws StatementException {
        if ( state.getSymTable().contains(this.varName) ){
            throw new StatementException("VARDECLSTMT:Variable with this name already exists!\n");
        }
        state.getSymTable().insert(this.varName, this.type.getDefaultValue());
        return null;
    }

    public String toString(){
        return type + " " + varName;
    }

    public IStatement deepCopy(){
        return new VarDeclStatement(varName, type);
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException {
        typeEnv.put(varName, type);
        return typeEnv;
    }
}
