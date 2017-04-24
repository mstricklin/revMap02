// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.xgraph;

import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.collect.FluentIterable;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import strickli.CovariantIterable;
import strickli.graph.*;

@Slf4j
@ToString
public class XVertex implements Vertex, Copyable<XVertex> {
    // TODO: make this a proxy, all operations happen on an inner class
    public static XVertex of(XGraph g, long id) {
        return new XVertex(g, id);
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
    void addInEdge(XEdge e) {
        inEdges.add( e.getRawId() );
    }
    void rmEdge(XEdge e) {
        outEdges.remove( e.getRawId() );
        inEdges.remove( e.getRawId() );
    }
    public Iterable<XEdge> getEdges() {
        log.info("getEdges {}", this);
        return newArrayList(transform(concat(inEdges, outEdges), graph.makeEdge));
    }

    @Override
    public Iterable<Edge> getEdges(String... labels) {
        log.info("getEdges generic {}", this);
        List<XEdge> al = newArrayList( transform( concat( inEdges, outEdges), graph.makeEdge));
        CovariantIterable<Edge> ci = CovariantIterable.of(al);
        return ci;
    }
    @Override
    public Iterable<Vertex> getVertices(String... labels) {
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
    private XVertex(XGraph g, long id) {
        graph = g;
        this.id = id;
        inEdges = newHashSet();
        outEdges = newHashSet();
    }
    private XVertex(XVertex v) {
        graph = v.graph;
        id = v.id;
        inEdges = newHashSet(v.inEdges);
        outEdges = newHashSet(v.outEdges);
    }
    // =================================
    private final XGraph graph;
    private final long id;
    private final Set<Long> inEdges;
    private final Set<Long> outEdges;
}
