// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.xgraph;

import lombok.extern.slf4j.Slf4j;
import strickli.graph.Copyable;
import strickli.graph.Edge;
import strickli.graph.Vertex;

@Slf4j
public class XEdge extends XElement implements Edge, Copyable<XEdge> {

    public static XEdge of(XGraph g, long id, XVertex out, XVertex in, String label) {
        XEdge e = new XEdge( g, id, out.getRawId(), in.getRawId(), label );
        if (null == e.impl()) {
            g.addEdgeImpl( id, RawEdge.of( id, out.getRawId(), in.getRawId(), label ) );
        }
        return e;
    }
    // =================================
    @Override
    public Vertex getOutVertex() {
        return XVertex.of( graph, getOutVertexID() );
    }
    @Override
    public Vertex getInVertex() {
        return XVertex.of( graph, getInVertexID() );
    }
    @Override
    public String getLabel() {
        return impl().label;
    }
    @Override
    public XEdge copy() {
        // TODO: copy RawVertex to revision cache?
        return new XEdge( this );
    }
    public long getOutVertexID() {
        return impl().outVertexID;
    }
    public long getInVertexID() {
        return impl().inVertexID;
    }
    @Override
    public void remove() {
        graph.removeEdge( this );
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
        super( g, id );
    }
    private XEdge(final XEdge e) {
        super( e.graph, e.id );
    }
    private RawEdge impl() {
        return graph.getEdgeImpl( id );
    }

    // =================================
    static class RawEdge extends RawElement {
        public static RawEdge of(long id, long outID, long inID, String label) {
            return new RawEdge( id, outID, inID, label );
        }
        private RawEdge(long id, long outID, long inID, String label) {
            super(id);
            this.label = label;
            this.outVertexID = outID;
            this.inVertexID = inID;
        }
        private RawEdge(final RawEdge other) {
            super(other);
            this.label = other.label;
            this.outVertexID = other.outVertexID;
            this.inVertexID = other.inVertexID;
        }
        // =================================
        private final long outVertexID, inVertexID;
        private final String label;
    }
}
