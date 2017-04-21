// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.xgraph;

import static com.google.common.collect.Sets.newHashSet;

import java.util.Collections;
import java.util.Set;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import strickli.graph.Copyable;
import strickli.graph.Direction;
import strickli.graph.Edge;
import strickli.graph.Vertex;

@Slf4j
@ToString
public class XVertex implements Vertex, Copyable<XVertex> {
    // TODO: make this a proxy, all operations happen on an inner class
    public static XVertex of(long id) {
        return new XVertex(id);
    }
    // =================================
    public Object getId() {
        return id;
    }
    public long getRawId() {
        return id;
    }
    void addOutEdge(XEdge e) {
        outEdges.add( e.getRawId() );
    }
    void rmOutEdge(XEdge e) {
        outEdges.remove( e.getRawId() );
    }
    void addInEdge(XEdge e) {
        inEdges.add( e.getRawId() );
    }
    void rmInEdge(XEdge e) {
        outEdges.remove( e.getRawId() );
    }
    public Iterable<XEdge> getEdges() {
        return Collections.emptyList();
    }

    @Override
    public Iterable<Edge> getOutEdges(String... labels) {
        return Collections.emptyList();
    }
    @Override
    public Iterable<Edge> getInEdges(String... labels) {
        return Collections.emptyList();
    }
    @Override
    public Iterable<Vertex> getOutVertices(String... labels) {
        return Collections.emptyList();
    }
    @Override
    public Iterable<Vertex> getInVertices(String... labels) {
        return Collections.emptyList();
    }
    @Override
    public Edge addEdge(String label, Vertex inVertex) {
        return null;
    }
    @Override
    public XVertex copy() {
        return new XVertex(this);
    }
    // =================================
    private XVertex(long id_) {
        id = id_;
        inEdges = newHashSet();
        outEdges = newHashSet();
    }
    private XVertex(XVertex v) {
        id = v.id;
        inEdges = newHashSet(v.inEdges);
        outEdges = newHashSet(v.outEdges);
    }
    // =================================
    private final long id;
    private final Set<Long> inEdges;
    private final Set<Long> outEdges;
}
