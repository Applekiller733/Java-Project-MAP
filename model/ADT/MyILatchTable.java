package model.ADT;

import java.util.HashMap;

public interface MyILatchTable {
    int getFree();
    void put(int key, int value);
    int get(int key);
    boolean containsKey(int key);
    void update(int key, int value);

    HashMap<Integer, Integer> getContent();
}
