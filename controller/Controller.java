package controller;

import exceptions.ControllerException;
import exceptions.ExpressionException;
import exceptions.RepositoryException;
import model.ADT.IHeap;
import model.ADT.MyIStack;
import model.state.PrgState;
import model.statements.IStatement;
import model.value.IValue;
import model.value.RefValue;
import repository.MyIRepository;
import repository.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller {
    private MyIRepository repository;
    public Controller(MyIRepository repository) {
        this.repository = repository;
    }
    public PrgState oneStep(PrgState state) throws ControllerException {
        try {
            MyIStack<IStatement> stack = state.getExecStack();
            IStatement currentstatement = stack.pop();
            return currentstatement.execute(state);
        }
        catch (Exception e) {
            throw new ControllerException(e.getMessage());
        }
    }

    public void allStep() throws ControllerException{
        try {
            PrgState prg = repository.getCurrentState();
            this.repository.logPrgStateExec();
            while (!prg.getExecStack().isEmpty()) {
                this.oneStep(prg);
//                this.repository.logPrgStateExec();
                prg.getHeap().setContent(safeGarbageCollector(this.getAllActiveAddresses(prg.getSymTable().getContent(),
                                                                    prg.getHeap()), prg.getHeap().getContent()));
                this.repository.logPrgStateExec();
            }
        }
        catch(Exception e) {
            throw new ControllerException(e.getMessage());
        }
    }

    public Map<Integer, IValue> safeGarbageCollector(List<Integer> activeAddresses,
                                                     Map<Integer, IValue> heap){
        return heap.entrySet().stream().filter( e -> activeAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }

    public List<Integer> getAllActiveAddresses(Collection<IValue> symTableValues, IHeap heap){
        return symTableValues.stream().filter(v -> v instanceof RefValue).map(v -> (RefValue) v).
                flatMap(
                        v -> {
                            List<Integer> addresses = new ArrayList<>();
                            while(true){
                                if (v.getAddress() == 0)
                                    break;
                                addresses.add(v.getAddress());
                                try{
                                    IValue next = heap.get(v.getAddress());
                                    if (next instanceof RefValue){
                                        v = (RefValue) next;
                                    }
                                } catch (ExpressionException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            return addresses.stream();
                        }
                ).collect(Collectors.toList());
    }

    public void addState(PrgState state){
        this.repository.addState(state);
    }
}
