package controller;

import exceptions.ControllerException;
import exceptions.RepositoryException;
import model.ADT.MyIStack;
import model.state.PrgState;
import model.statements.IStatement;
import repository.MyIRepository;
import repository.Repository;

public class Controller {
    private MyIRepository repository;
    public Controller(MyIRepository repository) {
        this.repository = repository;
    }
    public PrgState oneStep(PrgState state) throws ControllerException {
        MyIStack<IStatement> stack = state.getExecStack();
        if (stack.isEmpty()) {
            throw new ControllerException("Stack is empty");
        }
        try {
            IStatement currentstatement = stack.pop();
            return currentstatement.execute(state);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void allStep(){
        try {
            PrgState prg = repository.getCurrentState();
            this.repository.logPrgStateExec();
            while (!prg.getExecStack().isEmpty()) {
                this.oneStep(prg);
                this.repository.logPrgStateExec();
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
