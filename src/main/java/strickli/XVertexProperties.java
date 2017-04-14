// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli;

import strickli.cache.Revisable;

import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

public class XVertexProperties implements Revisable<XVertexProperties> {
    static XVertexProperties of(Integer id) {
        return new XVertexProperties(id);
    }
    // =================================
    public static class MutableXVertexProperties extends XVertexProperties {
        private MutableXVertexProperties(XVertexProperties baseline_, int id_) {
            super(id_);
            baseline = baseline_;
        }
        @Override
        public XVertexProperties mergeInto(XVertexProperties target) {
            // TODO: merge in properties
            return new XVertexProperties(id);
        }
        @Override
        public <T> T removeProperty(String key) {
            revised.remove(key);
            removed.add(key);
            return null; // TODO
        }
        @Override
        public void setProperty(final String key, final Object value) {
            removed.remove(key);
            revised.put(key, value);
        }
        // TODO: pointer back at immutable copy?
        private final XVertexProperties baseline;
        private final Map<String, Object> revised = newHashMap();
        private final Set<String> removed = newHashSet();
    }
    // =================================
    private XVertexProperties(int id_) {
        id = id_;
    }
    public XVertexProperties mutableCopy() {
        MutableXVertexProperties mvp = new MutableXVertexProperties(this, id);
        // TODO: copy props in...? or just a pointer to self?
        return mvp;
    }
    public XVertexProperties mergeInto(XVertexProperties target) {
        throw new UnsupportedOperationException();
    }
    public <T> T getProperty(String key) {
        return (T) properties.get(key);
    }
    public <T> T removeProperty(String key) {
        throw ExceptionFactory.immutableInstanceException();
    }
    public void setProperty(final String key, final Object value) {
        throw ExceptionFactory.immutableInstanceException();
    }
    public Object getID() {
        return id;
    }
    public int getRawID() {
        return id;
    }
    public int hashCode() {
        return id;
    }
    public boolean equals(final Object rhs) {
        if (this == rhs)
            return true;
        if (null == rhs)
            return false;
        if (!getClass().equals(rhs.getClass()))
            return false;
        return id == ((XVertexProperties) rhs).id;
    }

    protected final int id;
    protected final Map<String, Object> properties = newHashMap();
}
