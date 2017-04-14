// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.cache;

import com.google.common.reflect.TypeToken;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

@Slf4j
@ToString
@EqualsAndHashCode
public class BaselineCache<K, V> implements RevCache.BaselineCache<K, V> {
    public static <K, V> BaselineCache of() {
        return new BaselineCache();
    }
    @Override
    public V get(K k) {
        return null;
    }
    @Override
    public Iterable<K> keys() {
        return null;
    }
    @Override
    public Iterable<V> list() {
        return null;
    }
    @Override
    public boolean contains(K k) {
        return false;
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
    public Map<K, V> asMap() {
        return null;
    }
    @Override
    public void dump() {
        if (!log.isInfoEnabled())
            return;
        if (! baseline.isEmpty()) {
            log.info(" = BaselineCache cache {} => {}===========", keyType, valueType);
            for (Map.Entry e: baseline.entrySet()) {
                log.info(" {} => {}", e.getKey(), e.getValue());
            }
        }
    }
    // =================================
    private final Map<K, V> baseline = newHashMap();
    private final TypeToken<K> keyTypeToken = new TypeToken<K>(getClass()) { };
    private final Type keyType = keyTypeToken.getType();

    private final TypeToken<V> valueTypeToken = new TypeToken<V>(getClass()) { };
    private final Type valueType = valueTypeToken.getType();

}
