package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.MyIDictionary;
import model.ADT.MyIStack;
import model.expressions.VariableExpression;
import model.state.PrgState;
import model.type.BoolType;
import model.type.IType;
import model.expressions.IExpression;

public class ConditionalAssignmentStatement implements IStatement{
    private String varname;

    private IExpression exp1;
    private IExpression exp2;
    private IExpression exp3;

    public ConditionalAssignmentStatement(String varname, IExpression exp1, IExpression exp2, IExpression exp3) {
        this.varname = varname;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        MyIStack<IStatement> stack = state.getExecStack();
        IStatement conversiontoif = new IfStatement(exp1, new AssignStatement(varname, exp2), new AssignStatement(varname, exp3));
        stack.push(conversiontoif);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new ConditionalAssignmentStatement(varname, exp1, exp2, exp3);
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException {
        IType typevarname = new VariableExpression(varname).typecheck(typeEnv);
        IType type1 = exp1.typecheck(typeEnv);
        IType type2 = exp2.typecheck(typeEnv);
        IType type3 = exp3.typecheck(typeEnv);
        System.out.println(typevarname.toString() + type1.toString() + type2.toString() + type3.toString());
        if (type1.equals(new BoolType()) && type2.equals(typevarname) && type3.equals(typevarname)) {
            return typeEnv;
        }
        else
            throw new ExpressionException("CONDASSIGNSTMT: Type mismatch");
    }

    @Override
    public String toString(){
        return "ConditionalAssignment{"+
                "varname=" + varname.toString() + "\n" +
                ", exp1=" + exp1 + ", exp2=" + exp2 + ", exp3=" + exp3 + "}";
    }
}
