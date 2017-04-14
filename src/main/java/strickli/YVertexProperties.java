// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli;

import strickli.cache.Revisable;

import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

public class YVertexProperties implements Revisable<YVertexProperties> {
    @Override
    public YVertexProperties mutableCopy() {
        MutableYVertexProperties mvp = new MutableYVertexProperties(id);
        // TODO: copy props in...? or just a pointer to self?
        return mvp;
    }
    @Override
    public YVertexProperties mergeInto(YVertexProperties target) {
        throw new UnsupportedOperationException();
    }
    // =================================
    public static class MutableYVertexProperties extends YVertexProperties {
        private MutableYVertexProperties(int id_) {
            super(id_);
        }
        @Override
        public YVertexProperties mergeInto(YVertexProperties target) {
            return null;
        }
        // TODO: pointer back at immutable copy?
        private final Map<String, Object> revised = newHashMap();
        private final Set<String> removed = newHashSet();
    }
    // =================================
    private YVertexProperties(int id_) {
        id = id_;
    }
    // =================================

    protected final int id;
    protected final Map<String, Object> properties = newHashMap();
}
