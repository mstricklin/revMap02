// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.xgraph;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Queues.newArrayDeque;

import java.util.Deque;
import java.util.Queue;

import lombok.extern.slf4j.Slf4j;
import strickli.graph.*;

@Slf4j
public class XGraph implements Graph {
    private static long EDGE_ID = 0; // make Atomic
    private static long VERTEX_ID = 0; // make Atomic

    /*
    add vertex
    remove vertex
    add out edge to vertex
    remove out edge from vertex
    add in edge to vertex
    remove in edge from vertex


    add edge
    remove edge

    set property
    remove property
     */

    public static XGraph of() {
        return new XGraph();
    }

    public Vertex addVertex(Object id) {
        // TODO: prime ID counter
        XVertex v = XVertex.of( VERTEX_ID++ );
        log.trace( "addVertex {}", v );
        tx().applyAndQueue( Actions.addVertex( v ) );
        return v;
    }
    @Override
    public Vertex getVertex(Object id) {
        log.trace( "getVertex {}", id );
        checkNotNull( id );
        try {
            final Long longID = (id instanceof Long) ? (Long)id : Long.valueOf( id.toString() );
            return tx().rc.getVertex( longID );
        } catch (NumberFormatException | ClassCastException e) {
            log.error( "could not find vertex id {}", id );
        }
        return null;
    }
    @Override
    public void removeVertex(Vertex v) {
        log.trace( "removeVertex {}", v );
        checkNotNull( v );
        tx().applyAndQueue( Actions.removeVertex( (XVertex)v ) );
    }
//    Iterable<Vertex> getVertices();
//    Iterable<Vertex> getVertices(String key, Object val);
    @Override
    public Edge addEdge(Object id, Vertex outVertex, Vertex inVertex, String label) {
        checkNotNull( outVertex );
        checkNotNull( inVertex );
        XVertex xOut = (XVertex)outVertex;
        XVertex xIn = (XVertex)inVertex;
        XEdge e = XEdge.of( EDGE_ID++, xOut, xIn, label );
        log.trace( "addEdge {}", e );
        tx().applyAndQueue( Actions.addEdge( e ) );
        return e;
    }
    @Override
    public Edge getEdge(Object id) {
        log.trace( "getEdge {}", id );
        checkNotNull( id );
        try {
            final Long longID = (id instanceof Long) ? (Long)id : Long.valueOf( id.toString() );
            return tx().rc.getEdge( longID );
        } catch (NumberFormatException | ClassCastException e) {
            log.error( "could not find edge id {}", id );
        }
        return null;
    }
    @Override
    public void removeEdge(Edge e) {
        log.trace( "removeEdge {}", e );
        checkNotNull( e );
        XEdge xe = (XEdge)e;
        tx().applyAndQueue( Actions.removeEdge( xe ) );
    }
    //    Iterable<Edge> getEdges();
//    Iterable<Edge> getEdges(String key, Object val);
    @Override
    public void dump() {
        tx().dump();
        baseline.dump();
    }
    // =================================
    public void commit() { // TODO: throw
        Deque<Actions.Action> undo = newArrayDeque();
        TransactionWork tw = tx();

        synchronized (baseline) {
            for (Actions.Action a : tw.actions) {
                a.apply( baseline );
            }
        }
        tw.reset();
    }
    // =================================
    private TransactionWork tx() {
        return tx.get();
    }
    private ThreadLocal<TransactionWork> tx = new ThreadLocal<TransactionWork>() {
        @Override
        protected TransactionWork initialValue() {
            return new TransactionWork( baseline );
        }
    };

    private static class TransactionWork {
        private final RevisionCache rc;
        private final Queue<Actions.Action> actions = newArrayDeque();
        TransactionWork(BaselineCache baseline) {
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
        private Actions.Action applyAndQueue(Actions.Action a) {
            actions.add( a.apply( rc ) );
            return a;
        }
        private void reset() {
            rc.reset();
            actions.clear();
        }
    }


    private final BaselineCache baseline = new BaselineCache();
}
