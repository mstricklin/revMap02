// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.graph.zzz;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import strickli.xgraph.XEdge;
import strickli.xgraph.XVertex;

@Slf4j
public class RevisionCache implements Graph {
    RevisionCache(Graph baseline_) {
        baseline = baseline_;
    }
    @Override
    public XVertex addVertex(XVertex v) {
        revisedVertices.put( v.getRawId(), v );
        return v;
    }
    @Override
    public XVertex getVertex(Object id) {
        checkNotNull( id );
        try {
            final Long longID = (id instanceof Long) ? (Long)id : Long.valueOf(id.toString());

            if (removedVertices.contains(id))
                return null;
            XVertex v = revisedVertices.get(id);
            log.info("fetched {}=>{}", id, v);
            return (null != v) ? v : baseline.getVertex(id);
        } catch (NumberFormatException | ClassCastException e) {
            log.error("could not find vertex id {}", id);
        }
        return null;
    }
    @Override
    public XVertex removeVertex(XVertex v) {
        revisedVertices.remove( v.getRawId() ); // may be a no-op
        removedVertices.add( v.getRawId() );
        return v;
    }
    @Override
    public XEdge addEdge(XEdge e) {
        revisedEdges.put( e.getRawId(), e );
        return e;
    }
    @Override
    public XEdge getEdge(Object id) {
        return null;
    }
    @Override
    public XEdge removeEdge(XEdge e) {
        revisedEdges.remove( e.getRawId() ); // may be a no-op
        removedEdges.add( e.getRawId() );
        return e;
    }
    public void reset() {
        revisedVertices.clear();
        removedVertices.clear();
        revisedEdges.clear();
        removedEdges.clear();
    }
    // =================================
    @Override
    public void dump() {
        if (!log.isInfoEnabled())
            return;
        log.info("Transaction '{}'", Thread.currentThread().getId());
        if (!revisedVertices.isEmpty()) {
            log.info( " = vertices =" );
            for (Map.Entry<Long, XVertex> e: revisedVertices.entrySet())
                log.info(" {} => {}", e.getKey(), e.getValue());
        }
        if (!removedVertices.isEmpty())
            log.info(" Removed {}", removedVertices);

        if (!revisedEdges.isEmpty()) {
            log.info( " = edges =" );
            for (Map.Entry<Long, XEdge> e: revisedEdges.entrySet())
                log.info(" {} => {}", e.getKey(), e.getValue());
        }
        if (!removedEdges.isEmpty())
            log.info(" Removed {}", removedEdges);

    }
    // =================================
    private final Graph baseline;
    private final Map<Long, XVertex> revisedVertices = newHashMap();
    private final Set<Long> removedVertices = newHashSet();

    private final Map<Long, XEdge> revisedEdges = newHashMap();
    private final Set<Long> removedEdges = newHashSet();

}
