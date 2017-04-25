// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.xgraph;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import strickli.graph.Copyable;
import strickli.graph.Edge;
import strickli.graph.Graph;
import strickli.graph.Vertex;

@Slf4j
public class XEdge extends XElement implements Edge, Copyable<XEdge> {

    public static XEdge of(XGraph g, long id, XVertex out, XVertex in, String label) {
        return new XEdge(g, id, out.getRawId(), in.getRawId(), label);
    }
    // =================================

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
        return impl().label;
    }
    @Override
    public XEdge copy() {
        return new XEdge(this);
    }
    public long getOutVertexID() {
        return impl().outVertexID;
    }
    public long getInVertexID() {
        return impl().inVertexID;
    }
    @Override
    public String toString() {
        return "E[" + id
                + "][" + impl().outVertexID
                + "-" + impl().label
                + "->" + impl().inVertexID
                + "]";
    }
    // =================================
    private XEdge(XGraph g, long id, Long outID, Long inID, String label) {
        super(g, id);
    }
    private XEdge(final XEdge e) {
        super(e.graph, e.id);
    }
    private XEdge.Mutable impl() {
        return graph.getEdgeImpl( id );
    }
    static class Mutable {
        public static XEdge.Mutable of(long outID, long inID, String label) {
            return new Mutable( outID, inID, label );
        }
        private Mutable(long outID, long inID, String label) {
            this.label = label;
            this.outVertexID = outID;
            this.inVertexID = inID;
        }
        // =================================
        private final long outVertexID, inVertexID;
        private final String label;
    }
}
