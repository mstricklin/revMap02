// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.Data;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class GraphCache {

    public <K,V> RevMap.Revision getRevision2(Class<K> k, Class<V> v) {
        TypePair<K,V> tp = TypePair.of(k, v);
        RevMap rm = caches.getUnchecked(tp);

        return null;
    }
    public RevMap.Revision getRevision(TypePair<?,?> cacheKey) {
        Cache caches =  revisions.get();
        final RevMap rm = null;
//        try {
//            RevMap.Revision rmr = caches.get(cacheKey, new Callable<RevMap.Revision>() {
//                @Override
//                public RevMap.Revision call() throws Exception {
//                    return rm.getRevision();
//                }
//            });
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
        return rm.getRevision();
    }
    // =================================
    @Data(staticConstructor="of")
    private static class TypePair<K,V> {
        private final Class<K> keyType;
        private final Class<V> valueType;
    }

    LoadingCache<TypePair<?,?>, RevMap> caches = CacheBuilder.newBuilder()
            .initialCapacity(8)
            .build(new CacheLoader<TypePair<?,?>, RevMap>() {
                public RevMap load(TypePair<?,?> key) {
                    RevMap rm = RevMap.of();
                    return rm;
                }
            });

    private static class RevisionWork {
//        Revisions();
    }

//    XCache<TypePair<?,?>, RevMap> cache = CacheBuilder.newBuilder()
//            .maximumSize(1000)
//            .build();

    private ThreadLocal<Cache> revisions = new ThreadLocal<Cache>() {
        @Override protected Cache initialValue() {
            return CacheBuilder.newBuilder()
                    .maximumSize(1000)
                    .build();
        }
    };
}
