// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.xgraph;

import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import strickli.CovariantIterable;
import strickli.graph.*;

// XVertex needs a 'XGraph' for self-remove, which calls XGraph.remove
// XVertex needs a 'id' for lookup of RawVertex
// RawVertex needs id for hashCode and equals

@Slf4j
@ToString
public class XVertex extends XElement implements Vertex, Copyable<XVertex> {
    public static XVertex of(XGraph g, long id) {
        XVertex v = new XVertex(g, id);
        if (null == v.impl()) {
            g.addVertexImpl( id, RawVertex.of( id ) );
        }
        return v;
    }
    // =================================
    void addOutEdge(XEdge e) {
        impl().outEdges.add( e.getRawId() );
    }
    void addInEdge(XEdge e) {
        impl().inEdges.add( e.getRawId() );
    }
    void rmEdge(XEdge e) {
        RawVertex vi = impl();
        vi.outEdges.remove( e.getRawId() );
        vi.inEdges.remove( e.getRawId() );
    }
    @Override
    public Iterable<Edge> getEdges(Direction direction, String... labels) {
        // TODO: direction
        log.info("getEdges {}", this);
        RawVertex vi = impl();
        // TODO: check vi validity
        List<XEdge> al = newArrayList( transform( concat( vi.inEdges, vi.outEdges), graph.makeEdge));
        CovariantIterable<Edge> ci = CovariantIterable.of(al);
        return ci;
    }
    @Override
    public Iterable<Vertex> getVertices(Direction direction, String... labels) {
        return Collections.emptyList();
    }
    @Override
    public Edge addEdge(String label, Vertex inVertex) {
        return graph.addEdge( null, this, inVertex, label );
    }
    @Override
    public String toString() {
        return "V[" + id + "]";
    }
    @Override
    public XVertex copy() {
        // TODO: copy RawVertex to revision cache?
        return new XVertex(this);
    }
    @Override
    public void remove() {
        graph.removeVertex( this );
    }
    // =================================
    private XVertex(XGraph g, long id) {
        super(g, id);
    }

    private XVertex(XVertex v) {
        super(v.graph, v.id);
    }
    private RawVertex impl() {
        return graph.getVertexImpl( id );
    }
    // =================================
    static class RawVertex extends RawElement {
        public static RawVertex of(long id) {
            return new RawVertex( id );
        }
        public static RawVertex of(XVertex v) {
            return new RawVertex( v.id );
        }

        public RawVertex copy() {
            return new RawVertex( this );
        }
        private RawVertex(long id) {
            super(id);
            inEdges = newHashSet();
            outEdges = newHashSet();
        }
        private RawVertex(RawVertex other) {
            super(other);
            inEdges = newHashSet(other.inEdges);
            outEdges = newHashSet(other.outEdges);
        }
        // =================================
        private final Set<Long> inEdges;
        private final Set<Long> outEdges;
    }
}
