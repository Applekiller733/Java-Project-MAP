package model.value;

import model.type.BoolType;
import model.type.IType;
import model.type.IntType;

public class IntValue implements IValue{
    private int value;

    public IntValue(int number) {
        this.value = number;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public boolean equals(IValue obj) {
//        if (obj.getType() instanceof IntType) {
//            return this.value == ((IntValue) obj).getValue();
//        }
//        return false;
        return obj.getType() instanceof IntType && ((IntValue)obj).getValue() == this.getValue();
    }

    public String toString(){
        return Integer.toString(this.value);
    }
}
