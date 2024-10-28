package model.type;

import model.value.IValue;
import model.value.IntValue;

public class IntType implements IType{

    @Override
    public boolean equals(IType element){
        return element instanceof IntType;
    }

    public String toString(){
        return "int";
    }

    @Override
    public IValue getDefaultValue() {
        return new IntValue(0);
    }
}
