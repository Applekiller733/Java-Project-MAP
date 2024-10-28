package model.statements;

import model.state.PrgState;

public class NopStatement implements IStatement {

    public NopStatement() {};

    @Override
    public PrgState execute(PrgState state) {
        return null;
    }

    @Override
    public String toString() {
        return "NopStatement";
    }

    public IStatement deepCopy() {
        return new NopStatement();
    }
}
