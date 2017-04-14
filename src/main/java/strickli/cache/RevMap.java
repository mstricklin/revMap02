// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.cache;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Predicates.in;
import static com.google.common.base.Predicates.not;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.filter;
import static com.google.common.collect.Sets.newHashSet;

@Slf4j
public class RevMap<K, V extends Revisable> {
    public class Revision<K, V extends Revisable> extends RevMap<K, V> {
        private Revision(RevMap<K, V> baseline_) {
            baseline = baseline_;
        }
        V get(K k) {
            if (removed.contains(k))
                return null;
            V v = revised.get(k);
            return (null != v) ? v : baseline.get(k);
        }
        Iterable<K> keys() {
            return filter(Sets.union(revised.keySet(), baseline.items.keySet()),
                    not(in(removed)));
        }
        Iterable<V> list() {
            return null;
//            return items.values();
        }
        boolean contains(K k) {
            if (removed.contains(k))
                return false;
            return revised.containsKey(k) || items.containsKey(k);
        }
        int size() {
            return items.size();
        }
        boolean isEmpty() {
            return items.isEmpty();
        }
        Map<K, V> asMap() {
            return null;
//            return Collections.unmodifiableMap(items);
        }
        @Override
        void dump() {
            if (log.isInfoEnabled()) {
                log.info("=== Baseline ===");
                for (Map.Entry<K, V> e : baseline.items.entrySet())
                    log.info(" {} => {}", e.getKey(), e.getValue());
            }
        }
        void add(K k, V v) {
            revised.put(k, v);
        }
        void remove(K k) {
            revised.remove(k);
            removed.add(k);
        }
        void clear() {
            removed.addAll(baseline.items.keySet());
            revised.clear();
        }
        V getForUpdate(K k) {
            V v = baseline.get(k);
            if (null == v)
                return null;
            V vrevised = (V) v.mutableCopy();
            revised.put(k, vrevised);
            return vrevised;
        }

        void merge() {
            // TODO: lock
            for (Map.Entry<K, V> e : revised.entrySet()) {
                V blv = baseline.get(e.getKey());
                if (null == blv) {
                    baseline.items.put(e.getKey(), e.getValue());
                } else {
                    e.getValue().mergeInto(blv);
                }

            }
            for (K k : removed)
                baseline.items.remove(k);
            revised.clear();
            removed.clear();

        }
        void revert() {
            revised.clear();
            removed.clear();
        }

        private final RevMap<K, V> baseline;
        private final Set<K> removed = newHashSet();
        private final Map<K, V> revised = newHashMap();
    }
    // =================================
    static RevMap of() {
        return new RevMap();
    }

    Revision<K, V> getRevision() {
        return new Revision(this);
    }

    private V get(K k) {
        return items.get(k);
    }
    private Iterable<K> keys() {
        return items.keySet();
    }
    private Iterable<V> list() {
        return items.values();
    }
    private boolean contains(K k) {
        return items.containsKey(k);
    }
    private int size() {
        return items.size();
    }
    private boolean isEmpty() {
        return items.isEmpty();
    }
    private Map<K, V> asMap() {
        return Collections.unmodifiableMap(items);
    }

    void dump() {
        if (log.isInfoEnabled()) {
            log.info("=== Baseline ===");
            for (Map.Entry<K, V> e : items.entrySet())
                log.info(" {} => {}", e.getKey(), e.getValue());
        }
    }

    // TODO: read-through
    private final Map<K, V> items = newHashMap();
}
