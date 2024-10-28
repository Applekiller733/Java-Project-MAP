package model.ADT;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T>{

    List<T> list;

    public MyList() {
        list = new ArrayList<>();
    }

    @Override
    public void add(T element) {
        list.add(element);
    }

    @Override
    public List<T> getElements() {
        return list;
    }

    @Override
    public void remove(T element) {
        list.remove(element);
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();

        for (T el : list){
            str.append(el);
            str.append("\n");
        }

        return "The list contains: " + str.toString();
    }
}
