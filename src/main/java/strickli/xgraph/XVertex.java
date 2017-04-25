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
// XVertex needs a 'id' for lookup of Impl
//

@Slf4j
@ToString
public class XVertex extends XElement implements Vertex, Copyable<XVertex> {
    public static XVertex of(XGraph g, long id) {
        return new XVertex(g, id);
    }
    // =================================
    void addOutEdge(XEdge e) {
        impl().outEdges.add( e.getRawId() );
    }
    void addInEdge(XEdge e) {
        impl().inEdges.add( e.getRawId() );
    }
    void rmEdge(XEdge e) {
        Mutable vi = impl();
        vi.outEdges.remove( e.getRawId() );
        vi.inEdges.remove( e.getRawId() );
    }
    @Override
    public Iterable<Edge> getEdges(Direction direction, String... labels) {
        // TODO: direction
        log.info("getEdges {}", this);
        Mutable vi = impl();
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
        return null;
    }
    @Override
    public String toString() {
        return "V[" + id + "]";
    }
    @Override
    public XVertex copy() {
        return new XVertex(this);
    }
    // =================================
    private XVertex(XGraph g, long id) {
        super(g, id);
    }
    private XVertex(XVertex v) {
        super(v.graph, v.id);
    }
    private Mutable impl() {
        return graph.getVertexImpl( id );
    }
    // =================================
    static class Mutable {
        public static Mutable of(long id) {
            return new Mutable( id );
        }
        public static Mutable of(XVertex v) {
            return new Mutable( v.id );
        }

        public Mutable copy() {
            return new Mutable( this );
        }
        private Mutable(long id) {
            this.id = id;
            inEdges = newHashSet();
            outEdges = newHashSet();
        }
        private Mutable(Mutable v) {
            this.id = v.id;
            inEdges = newHashSet(v.inEdges);
            outEdges = newHashSet(v.outEdges);
        }
        // =================================
        protected final long id;
        private final Set<Long> inEdges;
        private final Set<Long> outEdges;
    }
}
