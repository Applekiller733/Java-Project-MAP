package model.ADT;

import exceptions.ExpressionException;
import model.value.IValue;

public interface IHeap {
    public Integer allocate(IValue name);
    public IValue get(Integer address) throws ExpressionException;
    public boolean exists(Integer address);
    public Integer getFreeLocation();
    public void set(Integer address, IValue value);
    public MyIDictionary getHeap();
}
