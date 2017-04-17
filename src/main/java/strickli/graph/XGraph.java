// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.graph;

import lombok.extern.slf4j.Slf4j;
import strickli.XEdge;
import strickli.XVertex;

import java.util.Deque;
import java.util.Queue;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Queues.newArrayDeque;

@Slf4j
public class XGraph {
    private static long EDGE_ID = 0; // make Atomic
    private static long VERTEX_ID = 0; // make Atomic

    final Graph g = new YGraph();
    Queue<Actions.Action> actions = newArrayDeque();

    XGraph() {

    }

    // =================================
    public XVertex addVertex(Object id) {
        // TODO: prime ID counter
        XVertex v = XVertex.of(VERTEX_ID++);
        log.trace("addVertex {}", v);
        Actions.Action a = Actions.addVertex(v);
        actions.add(a);
        a.apply(g);
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
        actions.add(a);
        a.apply(g);
    }

    // =================================
    public XEdge addEdge(Object id, XVertex outVertex, XVertex inVertex, String label) {
        XEdge e = XEdge.of(EDGE_ID++, outVertex, inVertex, label);
        log.trace("addEdge {}", e);
        Actions.Action a = Actions.addEdge(e);
        actions.add(a);
        a.apply(g);
        return e;
    }
    // =================================
    public XEdge getXEdge(Object id) {
        return null;
    }
    // =================================
    public void removeEdge(XEdge e) {
        checkNotNull(e);
        Actions.Action a = Actions.removeEdge(e);
        actions.add(a);
        a.apply(g);
    }
    // =================================
    public void commit() { // throws...
        Deque<Actions.Action> undo = newArrayDeque();
        Graph baseline = getBaseline();
        // lock baseline
        for (Actions.Action a : actions) {
            undo.push(a);
            // should operate on baseline
            a.apply(baseline);
        }
        // unlock baseline
        Graph wbSOR = getSOR();
        for (Actions.Action a : actions) {
            a.apply(wbSOR);
        }

        // catch
        // this is the order for undo...
        for (Actions.Action u : undo) {
            u.undo(baseline);
        }
        // unlock baseline
    }
    public void rollback() {

    }
    private Graph getBaseline() {
        return null;
    }
    private Graph getSOR() {
        return null;
    }
}
