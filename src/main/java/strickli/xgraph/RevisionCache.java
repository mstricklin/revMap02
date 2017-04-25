// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.xgraph;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RevisionCache implements XStore {
    RevisionCache(XGraph g, XStore baseline) {
        graph = g;
        this.baseline = baseline;
    }
    @Override
    public void addVertex(XVertex v) {
        revisedVertices2.put( v.getRawId(), XVertex.RawVertex.of( v ) );
        revisedVertices.put( v.getRawId(), v );
    }
    @Override
    public XVertex getVertex(Long id) {
        if (removedVertices2.contains( id ))
            return null;

        XVertex.RawVertex vm = revisedVertices2.get( id );
        return (null != vm) ? XVertex.of( graph, id )
                            : baseline.getVertex( id );
//        if (null != vm)
//            return XVertex.of( graph, id );
//        return baseline.getVertex( id );

//        XVertex v = revisedVertices.get( id );
//        return (null != v) ? v
//                           : baseline.getVertex( id );
    }
    // =================================
    public XVertex getMutableVertex(Long id) {
        if (removedVertices.contains( id ))
            return null;
        XVertex.RawVertex vm = revisedVertices2.get( id );
        if (null != vm)
            return XVertex.of( graph, id );
        XVertex v = baseline.getVertex( id );
        if (null != v) {
            revisedVertices2.put
        }

        XVertex v = revisedVertices.get( id );
        if (null != v)
            return v;
        v = baseline.getVertex( id );
        if (null != v) {
            v = v.copy();
            revisedVertices.put( id, v );
        }
        return v;
    }
    @Override
    public void removeVertex(XVertex v) {
        removedVertices2.add( v.getRawId() );
        revisedVertices2.remove( v.getRawId() );

        removedVertices.add( v.getRawId() );
        revisedVertices.remove( v.getRawId() );
    }
    public void removeVertex(XVertex.RawVertex v) {
        removedVertices2.add( v.id );
        revisedVertices2.remove( v.id );
    }
    @Override
    public void addEdge(XEdge e) {
        XVertex outVertex = getMutableVertex( e.getOutVertexID() );
        outVertex.addOutEdge( e );
        XVertex inVertex = getMutableVertex( e.getInVertexID() );
        inVertex.addInEdge( e );
        revisedEdges.put( e.getRawId(), e );
    }
    @Override
    public XEdge getEdge(Long id) {
        if (removedEdges.contains( id ))
            return null;
        XEdge e = revisedEdges.get( id );
        return (null != e) ? e
                           : baseline.getEdge( id );
    }
    public XEdge getMutableEdge(Long id) {
        if (removedEdges.contains( id ))
            return null;
        XEdge e = revisedEdges.get( id );
        if (null != e)
            return e;
        e = baseline.getEdge( id );
        return (e != null) ? e.copy() : null;
    }
    @Override
    public void removeEdge(XEdge e) {
        XVertex outVertex = getVertex( e.getOutVertexID() ); // test for null...
        outVertex.rmEdge( e );
        XVertex inVertex = getVertex( e.getInVertexID() ); // test for null...
        inVertex.rmEdge( e );

        removedEdges.add( e.getRawId() );
        revisedEdges.remove( e.getRawId() );
    }
    public void dump() {
        if (!log.isInfoEnabled())
            return;
        log.info( "Transaction '{}'", Thread.currentThread().getId() );
        if (!revisedVertices.isEmpty()) {
            log.info( "\t= vertices =" );
            for (Map.Entry<Long, XVertex> e : revisedVertices.entrySet())
                log.info( "\t {} => {}", e.getKey(), e.getValue() );
        }
        if (!removedVertices.isEmpty())
            log.info( "\tRemoved vertices {}", removedVertices );

        if (!revisedEdges.isEmpty()) {
            log.info( "\t= edges =" );
            for (Map.Entry<Long, XEdge> e : revisedEdges.entrySet())
                log.info( "\t {} => {}", e.getKey(), e.getValue() );
        }
        if (!removedEdges.isEmpty())
            log.info( "\tRemoved edges {}", removedEdges );
        if (revisedVertices.isEmpty() && removedVertices.isEmpty()
                && revisedEdges.isEmpty() && removedEdges.isEmpty())
            log.info( "\tempty" );
    }
    void reset() {
        revisedVertices.clear();
        removedVertices.clear();
        revisedEdges.clear();
        removedEdges.clear();
    }
    private final XStore baseline;
    private final XGraph graph;
    private final Map<Long, XVertex> revisedVertices = newHashMap();
    private final Set<Long> removedVertices = newHashSet();

    private final Map<Long, XVertex.RawVertex> revisedVertices2 = newHashMap();
    private final Set<Long> removedVertices2 = newHashSet();

    private final Map<Long, XEdge> revisedEdges = newHashMap();
    private final Set<Long> removedEdges = newHashSet();
}
