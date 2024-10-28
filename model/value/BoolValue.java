package model.value;

import model.type.BoolType;
import model.type.IType;

public class BoolValue implements IValue{
    private boolean value;

    public BoolValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public boolean equals(IValue obj) {
        return obj.getType() instanceof BoolType && ((BoolValue)obj).getValue() == this.getValue();
    }

    public String toString(){
        return Boolean.toString(this.value);
    }
}
