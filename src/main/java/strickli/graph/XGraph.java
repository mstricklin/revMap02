// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.graph;

import lombok.extern.slf4j.Slf4j;
import strickli.XEdge;
import strickli.XVertex;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class XGraph {
    private static long EDGE_ID = 0; // make Atomic
    private static long VERTEX_ID = 0; // make Atomic

    XGraph() {

    }

    // =================================
    public XVertex addVertex(Object id) {
        // TODO: get a unique ID, ignoring requested one
        XVertex v = XVertex.of(VERTEX_ID++);
        Actions.Action a = Actions.addVertex(v);
        a.apply(cache);
//        graphCache.vertexDao.create(v);
        return v;
    }
    // =================================
    public XVertex getVertex(Object id) {
        log.trace("getVertex {}", id);
        return null;
    }
    // =================================
    public void removeVertex(XVertex v) {
        checkNotNull(v);
        for (XEdge e : v.getEdges()) {
            removeEdge(e);
        }
        Actions.Action a = Actions.removeVertex(v);
    }
    // =================================
    public void addEdge(Object id, XVertex outVertex, XVertex inVertex, String label) {
        XEdge e = XEdge.of(EDGE_ID++, outVertex, inVertex, label);
        Actions.Action a = Actions.addEdge(e);
        a.apply(cache);
    }
    // =================================
    public void removeEdge(XEdge e) {
        checkNotNull(e);
        Actions.Action a = Actions.removeEdge(e);
        a.apply(cache);
    }
    private final XCache cache = XCache.of();
}
