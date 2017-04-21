// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.graph.zzz;

import lombok.extern.slf4j.Slf4j;
import strickli.xgraph.XEdge;
import strickli.xgraph.XVertex;

import java.util.Deque;
import java.util.Queue;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Queues.newArrayDeque;

@Slf4j
public class XGraph {
    private static long EDGE_ID = 0; // make Atomic
    private static long VERTEX_ID = 0; // make Atomic


    public static XGraph of() {
        return new XGraph();
    }
    // =================================
    private XGraph() {

    }
    // =================================
    public XVertex addVertex(Object id) {
        // TODO: prime ID counter
        XVertex v = XVertex.of( VERTEX_ID++ );
        transaction.get().applyAction( Actions.addVertex( v ) );
        log.trace( "addVertex {}", v );
        return v;
    }
    // =================================
    public XVertex getVertex(Object id) {
        XVertex v = transaction.get().rc.getVertex( id );
        log.trace( "getVertex {} {}", id, v );
        return v;
    }
    // =================================
    public void removeVertex(XVertex v) {
        checkNotNull( v );
        for (XEdge e : v.getEdges()) {
            removeEdge( e );
        }
        transaction.get().applyAction( Actions.removeVertex( v ) );
    }

    // =================================
    public XEdge addEdge(Object id, XVertex outVertex, XVertex inVertex, String label) {
        XEdge e = XEdge.of( EDGE_ID++, outVertex, inVertex, label );
        outVertex.addOutEdge( e );
        inVertex.addInEdge( e );
        log.trace( "addEdge {}", e );
        transaction.get().applyAction( Actions.addEdge( e ) );
        return e;
    }
    // =================================
    public XEdge getXEdge(Object id) {
        return null;
    }
    // =================================
    public void removeEdge(XEdge e) {
        checkNotNull( e );
        transaction.get().applyAction( Actions.removeEdge( e ) );
    }
    // =================================
    public void dump() {
        transaction.get().dump();
        baseline.dump();
    }
    // =================================
    public void commit() { // throws...
        Deque<Actions.Action> undo = newArrayDeque();
        TransactionWork tw = transaction.get();

        synchronized (baseline) {
            for (Actions.Action a : tw.actions) {
                a.apply( baseline );
            }
        }
        tw.reset();
//        for (Actions.Action a : actions) {
//            undo.push( a );
//            // should operate on baseline
//            a.apply( baseline );
//        }
//        // unlock baseline
//        Graph wbSOR = getSOR();
//        for (Actions.Action a : actions) {
//            a.apply( wbSOR );
//        }
//
//        // catch
//        // reverse order for undo...
//        for (Actions.Action u : undo) {
//            u.undo( baseline );
//        }
        // unlock baseline
    }
    public void rollback() {

    }
    private Graph getSOR() {
        return null;
    }
    // =================================

    private static class TransactionWork {
        private final RevisionCache rc;
        private final Queue<Actions.Action> actions = newArrayDeque();
        TransactionWork(Graph baseline) {
            rc = new RevisionCache( baseline );
        }
        private Actions.Action addAction(Actions.Action a) {
            actions.add( a );
            return a;
        }
        private void dump() {
            for (Actions.Action a : actions)
                log.info( "Action: {}", a );
            rc.dump();
        }
        private Actions.Action applyAction(Actions.Action a) {
            actions.add( a.apply( rc ) );
            return a;
        }
        private void reset() {
            rc.reset();
            actions.clear();
        }
    }

    private ThreadLocal<TransactionWork> transaction = new ThreadLocal<TransactionWork>() {
        @Override
        protected TransactionWork initialValue() {
            return new TransactionWork( baseline );
        }
    };

    private final Graph baseline = new BaselineCache();


}
