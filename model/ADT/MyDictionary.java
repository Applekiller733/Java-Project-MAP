package model.ADT;


import exceptions.ExpressionException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {
    private Map<K, V> map;

    public MyDictionary() {
        this.map = new HashMap<K, V>();
    }

    @Override
    public void insert(K key, V value) {
        this.map.put(key, value);
    }

    @Override
    public void remove(K key) throws ExpressionException{
        if (this.map.containsKey(key))
            this.map.remove(key);
        else
            throw new ExpressionException("Key not found in dictionary!\n");
    }

    @Override
    public void put(K key, V value) {

    }

    @Override
    public boolean contains(K key) {
        return this.map.containsKey(key);
    }

    @Override
    public V get(K key) throws ExpressionException {
        if (!this.map.containsKey(key)){
            throw new ExpressionException("Key not found in dictionary!\n");
        }
        return this.map.get(key);
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();

        for (K elem : this.map.keySet()){
            str.append(elem.toString() + "->" + this.map.get(elem).toString() + "\n");
        }

        return "Dictionary contains: " + str.toString();
    }

    @Override
    public Set<K> getKeys() {
        return this.map.keySet();
    }
}
