// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.graph.zzz;

import strickli.xgraph.XEdge;
import strickli.xgraph.XVertex;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

public class XCache implements XStore {
    public static XCache of() {
        return new XCache();
    }
    @Override
    public void addVertex(Long id, XVertex v) {
        vertexCache.put(id, v);
    }
    @Override
    public void removeVertex(Long id) {
        vertexCache.remove(id);
    }
    @Override
    public void addEdge(Long id, XEdge e) {
        edgeCache.put(id, e);
    }
    @Override
    public void removeEdge(Long id) {
        edgeCache.remove(id);
    }
    // =================================
    private final Map<Long, XVertex> vertexCache = newHashMap();
    private final Map<Long, XEdge> edgeCache = newHashMap();
}
