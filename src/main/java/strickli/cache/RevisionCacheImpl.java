// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.cache;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;

import static com.google.common.base.Predicates.in;
import static com.google.common.base.Predicates.not;
import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Maps.filterKeys;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

@Slf4j
public class RevisionCacheImpl<K, V> implements RevCache.RevisionCache<K, V> {
    public static <K, V> RevisionCacheImpl<K, V> of(RevCache.BaselineCache<K,V> baseline) {
        return new RevisionCacheImpl<>(baseline);
    }
    private RevisionCacheImpl(RevCache.BaselineCache<K,V> b) {
        baseline = b;
    }
    // =================================
    @Override
    public V get(K k) {
        return null;
    }
    @Override
    public Iterable<K> keys() {
        Iterable<K> keys = concat(baseline.keys(), revision.keySet());
        return filter(keys, not(in(removed)));
    }
    @Override
    public Iterable<V> list() {
        Map<K,V> baseValues = filterKeys(baseline.asMap(), not(in(removed)));
        return concat(baseValues.values(), revision.values());
    }
    @Override
    public boolean contains(K k) {
        if (removed.contains(k))
            return false;
        return revision.containsKey(k) || baseline.contains(k);
    }
    @Override
    public int size() {
        return 0;
    }
    // =================================
    @Override
    public void add(K k, V v) {
        revision.put(k, v);
    }
    @Override
    public void remove(K k) {
        revision.remove(k);
        removed.add(k);
    }
    @Override
    public void clear() {
        removed.addAll(revision.keySet());
        revision.clear();
    }
    @Override
    public V getForUpdate() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
    @Override
    public Map<K, V> asMap() {
        Map<K,V> baseValues = filterKeys(baseline.asMap(), not(in(removed)));
        return new ImmutableMap.Builder<K, V>()
                .putAll(baseValues)
                .putAll(revision)
                .build();
    }
    // =================================
    @Override
    public void merge() {

    }
    @Override
    public void revert() {

    }
    // =================================
    @Override
    public void dump() {
        if ( ! log.isInfoEnabled())
            return;
        if ( ! revision.isEmpty() ) {
            log.info(" = Revision ===========");
            for (Map.Entry e: revision.entrySet()) {
                log.info(" {} => {}", e.getKey(), e.getValue());
            }
        }
        if ( ! removed.isEmpty() ) {
            log.info(" = Removed ===========");
            log.info(" {}", removed);
        }
        baseline.dump();
    }
    // =================================
    private final RevCache.BaselineCache<K,V> baseline;
    private final Map<K, V> revision = newHashMap();
    private final Set<K> removed = newHashSet();
}
