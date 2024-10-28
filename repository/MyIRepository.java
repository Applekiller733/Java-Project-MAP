package repository;

import exceptions.RepositoryException;
import model.state.PrgState;

public interface MyIRepository {
    PrgState getCurrentState() throws RepositoryException;
    void addState(PrgState state);
    void logPrgStateExec() throws RepositoryException;
}
