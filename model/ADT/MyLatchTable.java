package model.ADT;

import java.util.HashMap;

public class MyLatchTable implements MyILatchTable{
    private HashMap<Integer,Integer> table;
    private int freeLoc = 0;

    public MyLatchTable(){
        table = new HashMap<>();
    }

    @Override
    public int getFree() {
        synchronized(this){
            freeLoc++;
            return freeLoc;
        }
    }

    @Override
    public void put(int key, int value) {
        synchronized (this) {
            table.put(key,value);
        }
    }

    @Override
    public int get(int key) {
        synchronized (this) {
            return table.get(key);
        }
    }

    @Override
    public boolean containsKey(int key) {
        synchronized (this) {
            return table.containsKey(key);
        }
    }

    @Override
    public void update(int key, int value) {
        synchronized (this) {
            table.put(key,value);
        }
    }

    @Override
    public HashMap<Integer, Integer> getContent() {
        synchronized (this) {
            return table;
        }
    }
}
