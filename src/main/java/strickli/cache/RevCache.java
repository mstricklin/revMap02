// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;


@Slf4j
public class RevCache {

    public interface BaselineCache<K, V> {
        // observe
        V get(K k);
        Iterable<K> keys();
        Iterable<V> list();
        boolean contains(K k);
        int size();
        boolean isEmpty();
        Map<K,V> asMap();

        void dump();
    }
    public interface RevisionCache<K, V> extends BaselineCache<K, V> {
        void add(K k, V v);
        void remove(K k);
        void clear();
        V getForUpdate();

        void merge();
        void revert();

    }


    Callable<BaselineCache> baselineCtor = new Callable<BaselineCache>() {
        @Override
        public BaselineCache call() throws Exception {
            return strickli.cache.BaselineCache.of();
        }
    };

    public <K,V> RevisionCache<?,?> getRevision(Class<K> k, Class<V> v) {
        final TypePair<K,V> tp = TypePair.of(k, v);
        try {
            BaselineCache<K,V> bl = caches2.get(tp, baselineCtor);
            RevisionCacheImpl ci = RevisionCacheImpl.of(bl);
            return ci;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


    // =================================
    @Data(staticConstructor="of")
    private static class TypePair<K,V> {
        private final Class<K> keyType;
        private final Class<V> valueType;
    }

    Cache<TypePair<?,?>, BaselineCache> caches2 = CacheBuilder.newBuilder()
            .initialCapacity(8)
            .build();

    LoadingCache<TypePair<?,?>, BaselineCache> caches3 = CacheBuilder.newBuilder()
            .initialCapacity(8)
            .build(new CacheLoader<TypePair<?,?>, BaselineCache>() {
                public BaselineCache load(TypePair<?,?> key) {
                    return null;
                }
            });


}
