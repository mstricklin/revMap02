// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.revMap;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

public class Revision <K,V> implements Map<K,V> {
    private Revision(RevMap<K,V> baseline_) {
        baseline = baseline_;
    }
    public RevMap<K,V> commit() {
        // TODO: copy in my changes
        revised.clear();
        removed.clear();
        return baseline;
    }
    public RevMap<K,V> rollback() {
        revised.clear();
        removed.clear();
        return baseline;
    }
    @Override
    public int size() {
        return 0;
    }
    @Override
    public boolean isEmpty() {
        return false;
    }
    @Override
    public boolean containsKey(Object o) {
        return false;
    }
    @Override
    public boolean containsValue(Object o) {
        return false;
    }
    @Override
    public V get(Object o) {
        return null;
    }
    @Override
    public V put(K k, V v) {
        return null;
    }
    @Override
    public V remove(Object o) {
        return null;
    }
    @Override
    public void putAll(Map<? extends K, ? extends V> map) {

    }
    @Override
    public void clear() {

    }
    @Override
    public Set<K> keySet() {
        return null;
    }
    @Override
    public Collection<V> values() {
        return null;
    }
    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }
    // =================================
    private final RevMap<K,V> baseline;
    private final Map<String, Object> revised = newHashMap();
    private final Set<String> removed = newHashSet();
}
