package repository;

import exceptions.RepositoryException;
import model.state.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements MyIRepository{
    private List<PrgState> instances;
    private String logFilePath;
    private int currentProgState = 0;
    public Repository(String logFilePath) {
        instances = new ArrayList<PrgState>();
        this.logFilePath = logFilePath;
    }
    public Repository(List<PrgState> instances, String logFilePath) {
        this.logFilePath = logFilePath;
        this.instances = instances;
    }
    public List<PrgState> getInstances() {
        return instances;
    }
    public PrgState getCurrentState() throws RepositoryException {
        if (instances.isEmpty()) {
            throw new RepositoryException("There is no current state\n");
        }
        return instances.get(currentProgState);
    }
    public void addState(PrgState state){
        instances.add(currentProgState, state);
    }

    public void logPrgStateExec() throws RepositoryException{
        try{
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath,true)));
        pw.println(this.getCurrentState());
        pw.close();
        }
        catch(IOException e){
            throw new RepositoryException(e.getMessage());
        }
    }
}
