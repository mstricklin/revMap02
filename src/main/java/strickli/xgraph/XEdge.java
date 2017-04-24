// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.xgraph;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import strickli.graph.Copyable;
import strickli.graph.Edge;
import strickli.graph.Graph;
import strickli.graph.Vertex;

@Slf4j
@ToString
public class XEdge implements Edge, Copyable<XEdge> {

    public static XEdge of(Graph g, long id, XVertex out, XVertex in, String label) {
        return new XEdge(g, id, out.getRawId(), in.getRawId(), label);
    }
    // =================================

    public Object getId() {
        return id;
    }
    public long getRawId() {
        return id;
    }


    @Override
    public Vertex getOutVertex() {
        return null;
    }
    @Override
    public Vertex getInVertex() {
        return null;
    }
    @Override
    public String getLabel() {
        return label;
    }
    @Override
    public XEdge copy() {
        return new XEdge(this);
    }
    public Long getOutVertexID() {
        return outVertexID;
    }
    public Long getInVertexID() {
        return inVertexID;
    }
    // =================================
    private XEdge(Graph g, long id, Long outID, Long inID, String label) {
        graph = g;
        this.id = id;
        this.label = label;
        this.outVertexID = outID;
        this.inVertexID = inID;
    }
    private XEdge(final XEdge e) {
        graph = e.graph;
        id = e.id;
        label = e.label;
        outVertexID = e.outVertexID;
        inVertexID = e.inVertexID;
    }
    private final Graph graph;
    private final long id;
    private final String label;
    private final long outVertexID, inVertexID;
}
