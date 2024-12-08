package model.ADT;

import exceptions.ExpressionException;

import java.util.Collection;
import java.util.Set;

public interface MyIDictionary<K, V> {
    void insert(K key, V value);
    void remove(K key) throws ExpressionException;
    void put(K key, V value);
    boolean contains(K key);
    V get(K key) throws ExpressionException;
    String toString();
    Collection<V> getContent();
    public Set<K> getKeys();
    public MyIDictionary<K, V> deepCopy();
}
