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

import javax.naming.ldap.Control;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private MyIRepository repository;
    private ExecutorService executor;

    public Controller(MyIRepository repository) {
        this.repository = repository;
        this.executor = Executors.newFixedThreadPool(8);
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<PrgState> prgList) {
        List<PrgState> prgStates = this.removeCompletedPrg(prgList);

        prgStates.forEach(prgState -> {
            try {
                repository.logPrgStateExec(prgState);
            } catch (RepositoryException e) {
                throw new RuntimeException(e);
            }
        });

        List<Callable<PrgState>> callList = prgStates.stream().map(p -> (Callable<PrgState>)() -> p.oneStep())
                .collect(Collectors.toList());
        try {
            List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }).filter(p -> p != null).collect(Collectors.toList());

            prgStates.addAll(newPrgList);

            prgStates.forEach(prgState -> {
                try {
                    repository.logPrgStateExec(prgState);
                } catch (RepositoryException e) {
                    throw new RuntimeException(e);
                }
            });
            repository.setPrgList(prgStates);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public void allStep() throws ControllerException{
        try {
            executor = Executors.newFixedThreadPool(2);
            List<PrgState> prgList = this.removeCompletedPrg(repository.getPrgStates());

            while (prgList.size() > 0){
                prgList.forEach(prgState -> {
                    prgState.getHeap().setContent(safeGarbageCollector(
                            this.getAllActiveAddresses(prgState.getSymTable().getContent(),
                            prgState.getHeap()), prgState.getHeap().getContent()));
                });
                oneStepForAllPrg(prgList);
                prgList = this.removeCompletedPrg(repository.getPrgStates());
            }
            executor.shutdownNow();
            repository.setPrgList(prgList);
        }
        catch(Exception e) {
            throw new ControllerException(e.getMessage());
        }
    }

    public Map<Integer, IValue> safeGarbageCollector(List<Integer> activeAddresses,
                                                     Map<Integer, IValue> heap){
        return heap.entrySet().stream().filter( e -> activeAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
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
                                    else
                                        break;
                                } catch (ExpressionException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            return addresses.stream();
                        }
                ).collect(Collectors.toList());
    }

    public List<PrgState> getCurrentProgramStates() throws RepositoryException {
        return repository.getPrgStates();
    }

    public void addState(PrgState state){
        this.repository.addState(state);
    }
}
