package model.ADT;

import exceptions.ExpressionException;
import model.value.IValue;

import java.util.Collection;
import java.util.Map;

public interface IHeap {
    public Integer allocate(IValue name);
    public IValue get(Integer address) throws ExpressionException;
    public boolean exists(Integer address);
    public Integer getFreeLocation();
    public void set(Integer address, IValue value);
    public void setContent(Map<Integer, IValue> content);
    public Map<Integer, IValue> getContent();
}
