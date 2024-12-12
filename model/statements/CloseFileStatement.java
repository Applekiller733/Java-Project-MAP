package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.MyIDictionary;
import model.state.PrgState;
import model.expressions.IExpression;
import model.type.IType;
import model.type.StringType;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseFileStatement implements IStatement {
    private IExpression exp;

    public CloseFileStatement(IExpression exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        if (!this.exp.evaluate(state.getSymTable(), state.getHeap()).getType().equals(new StringType())) {
            throw new ExpressionException("CLOSEFILESTMT:The expression is not a string");
        }
        StringValue eval = (StringValue) this.exp.evaluate(state.getSymTable(), state.getHeap());
        if (!state.getFileTable().contains(eval)){
            throw new StatementException("CLOSEFILESTMT:Filetable does not contain file");
        }
        BufferedReader reader = state.getFileTable().get(eval);
        try {
            reader.close();
            state.getFileTable().remove(eval);
        }
        catch (IOException e) {
            throw new StatementException("CLOSEFILESTMT:Could not close file");
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CloseFileStatement(exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException {
        //todo implement
        IType typeexp = this.exp.typecheck(typeEnv);
        if (typeexp.equals(new StringType())) {
            return typeEnv;
        }
        else
            throw new ExpressionException("CLOSEFILESTMT:The expression is not a string");
    }

    @Override
    public String toString(){
        return "close(" + exp.toString() + ")";
    }
}
