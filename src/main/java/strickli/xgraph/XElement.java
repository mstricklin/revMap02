// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.xgraph;

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

    }
    @Override
    public <T> T removeProperty(String key) {
        return null;
    }
    @Override
    public void remove() {

    }
    // =================================
    protected final long id;
    protected final XGraph graph;

}
