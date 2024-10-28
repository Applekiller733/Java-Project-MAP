package model.value;

import model.type.IType;

public interface IValue {
    IType getType();
    boolean equals(IValue value);
}
