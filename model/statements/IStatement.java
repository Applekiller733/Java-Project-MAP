package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.MyIDictionary;
import model.state.PrgState;
import model.type.IType;

public interface IStatement {
    PrgState execute(PrgState state) throws StatementException, ExpressionException;
    IStatement deepCopy();
    MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException;
}
