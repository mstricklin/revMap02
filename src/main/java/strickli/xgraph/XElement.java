// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.xgraph;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;
import java.util.Set;

import strickli.graph.Element;

public abstract class XElement implements Element {
    public XElement(XGraph graph, long id) {
        this.graph = graph;
        this.id = id;
    }
    @Override
    public Object getId() {
        return id;
    }
    public long getRawId() {
        return id;
    }
    @Override
    public <T> T getProperty(String key) {
        return null;
    }
    @Override
    public Set<String> getPropertyKeys() {
        return null;
    }
    @Override
    public void setProperty(String key, Object value) {
        // get for mutation

    }
    @Override
    public <T> T removeProperty(String key) {
        // get for mutation
        return null;
    }
    @Override
    public abstract void remove();
    // =================================
    protected final long id;
    protected final XGraph graph;
    // =================================
    abstract static class RawElement {
        RawElement(long id) {
            this.id = id;
            properties = newHashMap();
        }
        RawElement(final RawElement other) {
            this.id = other.id;
            properties = newHashMap(other.properties);
        }
        @Override
        public int hashCode() {
            return (int)id;
        }
        @Override
        public boolean equals(Object o) {
            if (null == o) return false;
            if (this == o) return true;
            if ( ! getClass().equals( o.getClass())) return false;
            return (((RawElement)o).id == id);
        }
        protected final long id;
        protected final Map<String, Object> properties;
    }

}
