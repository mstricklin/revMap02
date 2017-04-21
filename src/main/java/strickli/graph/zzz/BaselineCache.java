// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.graph.zzz;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import strickli.xgraph.XEdge;
import strickli.xgraph.XVertex;

@Slf4j
public class BaselineCache implements Graph {
    @Override
    public XVertex addVertex(XVertex v) {
        vertices.put( v.getRawId(), v );
        return v;
    }
    @Override
    public XVertex getVertex(Object id) {
        return null;
    }
    @Override
    public XVertex removeVertex(XVertex v) {
        vertices.remove( v.getRawId() );
        return null;
    }
    @Override
    public XEdge addEdge(XEdge e) {
        edges.put( e.getRawId(), e );
        return e;
    }
    @Override
    public XEdge getEdge(Object id) {
        return null;
    }
    @Override
    public XEdge removeEdge(XEdge e) {
        edges.remove( e.getRawId() );
        return e;
    }
    @Override
    public void dump() {
        if (!log.isInfoEnabled())
            return;
        log.info("Baseline");
        if (!vertices.isEmpty()) {
            log.info( " = vertices =" );
            for (Map.Entry<Long, XVertex> e: vertices.entrySet())
                log.info(" {} => {}", e.getKey(), e.getValue());
        }

        if (!edges.isEmpty()) {
            log.info( " = edges =" );
            for (Map.Entry<Long, XEdge> e: edges.entrySet())
                log.info(" {} => {}", e.getKey(), e.getValue());
        }

    }
    // =================================
    private final Map<Long, XVertex> vertices = newHashMap();
    private final Map<Long, XEdge> edges = newHashMap();
}
