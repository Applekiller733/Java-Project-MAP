package model.ADT;

import exceptions.ExpressionException;
import model.value.IValue;

public class Heap implements IHeap{

    private MyIDictionary<Integer,IValue> map;
    private Integer freeLocation;

    public Heap(){
        map = new MyDictionary<>();
        freeLocation = 1;
    }

    @Override
    public Integer allocate(IValue name) {
        map.insert(freeLocation++, name);
        return freeLocation-1;
    }

    @Override
    public IValue get(Integer address) throws ExpressionException {
        return map.get(address);
    }

    @Override
    public boolean exists(Integer address) {
        return map.contains(address);
    }

    @Override
    public Integer getFreeLocation() {
        return freeLocation;
    }

    @Override
    public void set(Integer address, IValue value) {
        map.insert(address,value);
    }

    @Override
    public MyIDictionary getHeap() {
        return map;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("Heap:\n");
        map.getKeys().forEach(key-> {
            try {
                builder.append(key.toString() + "->" + map.get(key).toString());
            } catch (ExpressionException e) {
                builder.append("");
            }
        });
        return builder.toString();
    }
}
