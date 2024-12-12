package model.statements;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.type.IType;
import model.type.StringType;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;

public class OpenFileStatement implements IStatement{
    private IExpression exp;

    public OpenFileStatement(IExpression exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        if (!this.exp.evaluate(state.getSymTable(), state.getHeap()).getType().equals(new StringType())){
            throw new ExpressionException("OPENFILESTMT:Expression does not evaluate to a string");
        }
        StringValue val =(StringValue) this.exp.evaluate(state.getSymTable(), state.getHeap());

        if (state.getFileTable().contains(val)){
            throw new ExpressionException("OPENFILESTMT:File already exists in table");
        }
        try {
            BufferedReader reader = new BufferedReader((new FileReader((this.exp.evaluate(state.getSymTable(), state.getHeap())).toString())));
            state.getFileTable().insert(val, reader);
        } catch (Exception e) {
            throw new ExpressionException("OPENFILESTMT:File not found");
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new OpenFileStatement(this.exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException {
        //todo implement
        IType typeexp = exp.typecheck(typeEnv);
        if (typeexp.equals(new StringType())){
            return typeEnv;
        }
        else
            throw new StatementException("OPENFILESTMT:Expression does not evaluate to a string");
    }

    @Override
    public String toString() {
        return "open(" + this.exp.toString() + ")";
    }
}
