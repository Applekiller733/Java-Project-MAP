package repository;

import exceptions.RepositoryException;
import model.state.PrgState;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public interface MyIRepository {
//    PrgState getCurrentState() throws RepositoryException;
    void addState(PrgState state);
    void logPrgStateExec(PrgState state) throws RepositoryException;
    List<PrgState> getPrgStates() throws RepositoryException;
    void setPrgList(List<PrgState> prgStates);
}
