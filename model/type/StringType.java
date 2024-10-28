package model.type;

import model.value.IValue;
import model.value.StringValue;

public class StringType implements IType {

    @Override
    public boolean equals(IType type) {
        return type instanceof StringType;
    }

    @Override
    public IValue getDefaultValue() {
        return new StringValue("") ;
    }

    public String toString(){
        return "StringType";
    }
}
