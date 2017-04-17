// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.graph;

import strickli.XEdge;
import strickli.XVertex;

import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

public class XRevisedCache implements XStore {
    @Override
    public void addVertex(Long id, XVertex v) {

    }
    @Override
    public void removeVertex(Long vID) {

    }
    @Override
    public void addEdge(Long id, XEdge v) {

    }
    @Override
    public void removeEdge(Long id) {

    }
    // =================================
    private final Map<Long, XVertex> vertexCache = newHashMap();
    private final Set<Long> vertexRemoved = newHashSet();
    private final Map<Long, XEdge> edgeCache = newHashMap();
    private final Set<Long> edgeRemoved = newHashSet();
}
