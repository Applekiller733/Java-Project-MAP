package model.ADT;

import exceptions.ExpressionException;
import model.value.IValue;

import java.util.HashMap;
import java.util.Map;

public class Heap implements IHeap{

    private Map<Integer,IValue> map;
    private Integer freeLocation;

    public Heap(){
        map = new HashMap<>();
        freeLocation = 1;
    }

    @Override
    public Integer allocate(IValue name) {
        map.put(freeLocation, name);
        freeLocation++;
        return freeLocation-1;
    }

    @Override
    public IValue get(Integer address) throws ExpressionException {
        return map.get(address);
    }

    @Override
    public boolean exists(Integer address) {
        return map.containsKey(address);
    }

    @Override
    public Integer getFreeLocation() {
        return freeLocation;
    }

    @Override
    public void set(Integer address, IValue value) {
        if (!this.map.containsKey(address)) {
            throw new RuntimeException("Map address not in heap!");
        }
        map.put(address,value);
    }

    @Override
    public void setContent(Map<Integer, IValue> content) {
        this.map = content;
    }

    @Override
    public Map<Integer, IValue> getContent() {
        return map;
    }


    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("Heap:\n");
//        map.keySet().forEach(key-> {
//                builder.append(key.toString() + "->" + map.get(key).toString());
//        });
        for (Map.Entry<Integer, IValue> entry : map.entrySet()) {
            builder.append(entry.getKey() + "-> " + entry.getValue() + "\n");
        }
        return builder.toString();
    }
}
