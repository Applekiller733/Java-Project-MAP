package model.ADT;

import java.util.List;

public interface MyIList<T> {


    void add(T element);
    List<T> getElements();
    void remove(T element);
    String toString();
}
