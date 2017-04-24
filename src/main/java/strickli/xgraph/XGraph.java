// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.xgraph;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Queues.newArrayDeque;

import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.base.Function;
import lombok.extern.slf4j.Slf4j;
import strickli.graph.*;

@Slf4j
public class XGraph implements Graph, TransactionalGraph {
    private static AtomicLong EDGE_ID = new AtomicLong( 0L );
    private static AtomicLong VERTEX_ID = new AtomicLong( 0L );


    /*
    add vertex
    remove vertex

    add out edge to vertex // within xstore? different semantics based on storage medium...
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
        XVertex v = XVertex.of( this, VERTEX_ID.getAndIncrement() );
        log.trace( "addVertex {}", v );
        applyAndQueue( Actions.addVertex( v ) );
        return v;
    }
    @Override
    public Vertex getVertex(Object id) {
        log.trace( "getVertex {}", id );
        checkNotNull( id );
        try {
            final Long longID = (id instanceof Long) ? (Long)id : Long.valueOf( id.toString() );
            return rc().getVertex( longID );
        } catch (NumberFormatException | ClassCastException e) {
            log.error( "could not find vertex id {}", id );
        }
        return null;
    }
    public XVertex.Mutable getVertexImpl(Long id) {
        log.trace( "getVertexImpl {}", id );
        return rc().getVertexImpl( id );
    }
    @Override
    public void removeVertex(Vertex v) {
        log.trace( "removeVertex {}", v );
        checkNotNull( v );
        for (Edge e : v.getEdges( Direction.BOTH )) {
            log.trace( "remove edge {}", e );
            removeEdge( e );
        }
        applyAndQueue( Actions.removeVertex( (XVertex)v ) );
    }
    //    Iterable<Vertex> getVertices();
//    Iterable<Vertex> getVertices(String key, Object val);
    @Override
    public Edge addEdge(Object id, Vertex outVertex, Vertex inVertex, String label) {
        checkNotNull( outVertex );
        checkNotNull( inVertex );
        XEdge e = XEdge.of( this, EDGE_ID.getAndIncrement(), (XVertex)outVertex, (XVertex)inVertex, label );
        log.trace( "addEdge {}", e );
        applyAndQueue( Actions.addEdge( e ) );
        return e;
    }
    @Override
    public Edge getEdge(Object id) {
        log.trace( "getEdge {}", id );
        checkNotNull( id );
        try {
            final Long longID = (id instanceof Long) ? (Long)id : Long.valueOf( id.toString() );
            return rc().getEdge( longID );
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
        applyAndQueue( Actions.removeEdge( xe ) );
    }
    //    Iterable<Edge> getEdges();
//    Iterable<Edge> getEdges(String key, Object val);
    // =================================
    protected Function<Long, XEdge> makeEdge = new Function<Long, XEdge>() {
        @Override
        public XEdge apply(Long id) {
            return rc().getEdge( id );
        }
    };
    @Override
    public void dump() {
        tx.get().dump();
        baseline.dump();
    }
    @Override
    public void shutdown() {

    }
    // =================================
    public void commit() { // TODO: throw
        Deque<Actions.Action> undo = newArrayDeque();
        TransactionWork tw = tx.get();

        synchronized (baseline) {
            for (Actions.Action a : tw.actions) {
                a.apply( baseline );
            }
        }
        tw.reset();
    }
    @Override
    public void rollback() {

    }
    // =================================
    public String toString() {
        return Integer.toString( hashCode() );
    }
    // =================================
    private Actions.Action applyAndQueue(Actions.Action a) {
        tx.get().applyAndQueue( a );
        return a;
    }
    private RevisionCache rc() {
        return tx.get().rc;
    }
    //    private TransactionWork tx() {
//        return tx.get();
//    }
    private ThreadLocal<TransactionWork> tx = new ThreadLocal<TransactionWork>() {
        @Override
        protected TransactionWork initialValue() {
            return new TransactionWork( XGraph.this, baseline );
        }
    };

    private static class TransactionWork {
        private final RevisionCache rc;
        private final Queue<Actions.Action> actions = newArrayDeque();
        TransactionWork(XGraph g, BaselineCache baseline) {
            rc = new RevisionCache( g, baseline );
        }
        private Actions.Action addAction(Actions.Action a) {
            actions.add( a );
            return a;
        }
        private void dump() {
//            for (Actions.Action a : actions)
//                log.info( "Action: {}", a );
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


    private final BaselineCache baseline = new BaselineCache( this );
}
