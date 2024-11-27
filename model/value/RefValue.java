package model.value;

import model.type.IType;
import model.type.RefType;

public class RefValue implements IValue {
    private int address;
    private IType locationType;

    public RefValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    public int getAddress() {
        return this.address;
    }

    public IType getLocationType() {
        return this.locationType;
    }

    @Override
    public boolean equals(IValue value) {
        return value.getType().equals(new RefType(locationType)) && ((RefValue) value).address == address;
    }

    @Override
    public String toString() {
        return "Ref(" + address + "," + locationType + ")";
    }
}
























/*
public class RefValue implements IValue{
    private int address;
    private IType locationType;


    public RefValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    int getAddress() {
        return address;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    @Override
    public boolean equals(IValue value) { //todo implement
        return false;
    }
}
*/