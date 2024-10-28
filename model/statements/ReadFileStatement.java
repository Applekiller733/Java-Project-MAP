package model.statements;

import model.ADT.MyIDictionary;
import model.expressions.IExpression;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.state.PrgState;
import model.type.IntType;
import model.type.StringType;
import model.value.IValue;
import model.value.IntValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement {
    private IExpression expression;
    private String var_name;
    public ReadFileStatement(IExpression exp, String var_name) {
        this.expression = exp;
        this.var_name = var_name;
    }
    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        if (!state.getSymTable().contains(this.var_name)) {
            throw new StatementException("Variable '" + this.var_name + "' not defined");
        }
        if (state.getSymTable().get(this.var_name).getType().equals(new IntType()) ) {
            throw new StatementException("The variable is not an integer type");
        }
        IValue eval = this.expression.evaluate(state.getSymTable());
        if (!eval.getType().equals(new StringType())){
            throw new ExpressionException("The expression does not evaluate to a string type");
        }
        if (!state.getFileTable().contains(((StringValue) eval))){
            throw new StatementException("The file does not exist");
        }
        BufferedReader reader = state.getFileTable().get(((StringValue) eval));
        try {
            String fileLine = reader.readLine();
            if (fileLine == null) {
                fileLine = "0";
            }
            int intFileLine = Integer.parseInt(fileLine);
            state.getSymTable().insert(this.var_name, new IntValue(intFileLine));
        }
        catch (IOException e) {
            throw new StatementException("Error reading file");
        }
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new ReadFileStatement(expression.deepCopy(), var_name);
    }
}
