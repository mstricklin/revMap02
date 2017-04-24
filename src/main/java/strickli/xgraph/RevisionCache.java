// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.xgraph;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RevisionCache implements XStore {
    RevisionCache(XStore baseline_) {
        baseline = baseline_;
    }
    @Override
    public void addVertex(XVertex v) {
        revisedVertices.put(v.getRawId(), v);
    }
    @Override
    public XVertex getVertex(Long id) {
        if (removedVertices.contains( id ))
            return null;
        XVertex v = revisedVertices.get( id );
        return (null != v) ? v
                           : baseline.getVertex( id );
    }
    public XVertex getMutableVertex(Long id) {
        if (removedVertices.contains( id ))
            return null;
        XVertex v = revisedVertices.get( id );
        if (null != v)
            return v;
        v = baseline.getVertex( id );
        if (null != v) {
            v = v.copy();
            revisedVertices.put(id, v);
        }
        return v;
    }
    @Override
    public void removeVertex(XVertex v) {
        removedVertices.add( v.getRawId() );
        revisedVertices.remove( v.getRawId() );
    }
    @Override
    public void addEdge(XEdge e) {
        XVertex outVertex = getMutableVertex( e.getOutVertexID() );
        outVertex.addOutEdge( e );
        XVertex inVertex = getMutableVertex( e.getInVertexID() );
        inVertex.addInEdge( e );
        revisedEdges.put(e.getRawId(), e);
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
        log.info("Transaction '{}'", Thread.currentThread().getId());
        if (!revisedVertices.isEmpty()) {
            log.info( "\t= vertices =" );
            for (Map.Entry<Long, XVertex> e: revisedVertices.entrySet())
                log.info("\t {} => {}", e.getKey(), e.getValue());
        }
        if (!removedVertices.isEmpty())
            log.info("\tRemoved vertices {}", removedVertices);

        if (!revisedEdges.isEmpty()) {
            log.info( "\t= edges =" );
            for (Map.Entry<Long, XEdge> e: revisedEdges.entrySet())
                log.info("\t {} => {}", e.getKey(), e.getValue());
        }
        if (!removedEdges.isEmpty())
            log.info("\tRemoved edges {}", removedEdges);
        if (revisedVertices.isEmpty() && removedVertices.isEmpty()
                && revisedEdges.isEmpty() && removedEdges.isEmpty())
            log.info("\tempty");
    }
    void reset() {
        revisedVertices.clear();
        removedVertices.clear();
        revisedEdges.clear();
        removedEdges.clear();
    }
    private final XStore baseline;
    private final Map<Long, XVertex> revisedVertices = newHashMap();
    private final Set<Long> removedVertices = newHashSet();

    private final Map<Long, XEdge> revisedEdges = newHashMap();
    private final Set<Long> removedEdges = newHashSet();
}
