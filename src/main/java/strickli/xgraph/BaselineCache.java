// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.xgraph;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaselineCache implements XStore {
    BaselineCache(XGraph g) {
        graph = g;
    }
    // TODO: add read-through

    @Override
    public void addVertex(XVertex v) {
        vertices.put( v.getRawId(), v );
        verticesImpl.put(v.getRawId(), XVertex.Mutable.of(v.getRawId()));
    }

    @Override
    public XVertex getVertex(Long id) {
        // TODO: read-through to SoR
        XVertex.Mutable vm = verticesImpl.get( id );
        return (vm == null) ? null : XVertex.of( graph, id );
    }
    @Override
    public void removeVertex(XVertex v) {
        verticesImpl.remove( v.getRawId() );
        vertices.remove( v.getRawId() );
    }
    @Override
    public void addEdge(XEdge e) {
        XVertex outVertex = getVertex( e.getOutVertexID() );
        outVertex.addOutEdge( e );
        XVertex inVertex = getVertex( e.getInVertexID() );
        inVertex.addInEdge( e );
        edges.put( e.getRawId(), e );
    }
    @Override
    public XEdge getEdge(Long id) {
        // TODO: read-through to SoR
        return edges.get( id );
    }
    @Override
    public void removeEdge(XEdge e) {
        XVertex outVertex = getVertex( e.getOutVertexID() );
        outVertex.rmEdge( e );
        XVertex inVertex = getVertex( e.getInVertexID() );
        inVertex.rmEdge( e );
        edges.remove( e.getRawId() );
    }
    @Override
    public void dump() {
        if (!log.isInfoEnabled())
            return;
        log.info("Baseline");
        if (!vertices.isEmpty()) {
            log.info( "\t= vertices =" );
            for (Map.Entry<Long, XVertex> e: vertices.entrySet())
                log.info("\t {} => {}", e.getKey(), e.getValue());
        }

        if (!edges.isEmpty()) {
            log.info( "\t= edges =" );
            for (Map.Entry<Long, XEdge> e: edges.entrySet())
                log.info("\t {} => {}", e.getKey(), e.getValue());
        }
        if (vertices.isEmpty() && edges.isEmpty())
            log.info("\tempty");
    }
    // =================================
    private final XGraph graph;
    private final Map<Long, XVertex> vertices = newHashMap();
    private final Map<Long, XVertex.Mutable> verticesImpl = newHashMap();
    private final Map<Long, XEdge> edges = newHashMap();
}
