// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.xgraph;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

import java.util.Map;
import java.util.Set;

public class RevCache<T> implements XCache<T> {
    public static <T> RevCache<T> newRevCache() {
        return new RevCache<>();
    }
    public void add(Long id, T t) {
        store.put(id, t);
    }
    public T get(Long id) {
        return store.get( id );
    }
    public void remove(Long id) {
        store.remove( id );
    }
    @Override
    public void reset() {
        store.clear();
    }
    public XCache<T> getRevision() {
        return new Revision<>( this );
    }
    Map<Long, T> store = newHashMap();
    // =================================
    public static class Revision<T> implements XCache<T> {
        Revision(RevCache<T> baseline) {
            this.baseline = baseline;
        }

        @Override
        public void add(Long id, T t) {
            revised.put( id, t );
        }
        @Override
        public T get(Long id) {
            if (removed.contains( id ))
                return null;
            T t = revised.get( id );
            return (null != t) ? t
                               : baseline.get( id );
        }
        @Override
        public void remove(Long id) {
            removed.add( id );
            revised.remove( id );
        }
        public void reset() {
            revised.clear();
            removed.clear();
        }
        // =================================
        final RevCache<T> baseline;
        private final Map<Long, T> revised = newHashMap();
        private final Set<Long> removed = newHashSet();
    }
}
