package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.state.PrgState;
import model.expressions.IExpression;
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
        if (!this.exp.evaluate(state.getSymTable()).getType().equals(new StringType())) {
            throw new ExpressionException("The expression is not a string");
        }
        StringValue eval = (StringValue) this.exp.evaluate(state.getSymTable());
        if (!state.getFileTable().contains(eval)){
            throw new StatementException("Filetable does not contain file");
        }
        BufferedReader reader = state.getFileTable().get(eval);
        try {
            reader.close();
            state.getFileTable().remove(eval);
        }
        catch (IOException e) {
            throw new StatementException("Could not close file");
        }
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new CloseFileStatement(exp.deepCopy());
    }

    @Override
    public String toString(){
        return "close(" + exp.toString() + ")";
    }
}
