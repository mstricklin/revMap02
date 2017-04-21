// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.xgraph;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import strickli.graph.Copyable;
import strickli.graph.Edge;
import strickli.graph.Vertex;

@Slf4j
@ToString
public class XEdge implements Edge, Copyable<XEdge> {

    public static XEdge of(long id, XVertex out, XVertex in, String label) {
        return new XEdge(id, out.getRawId(), in.getRawId(), label);
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
        return null;
    }
    public Long getOutID() {
        return outID;
    }
    public Long getInID() {
        return inID;
    }
    // =================================
    private XEdge(long id_, Long outID_, Long inID_, String label_) {
        id = id_;
        label = label_;
        outID = outID_;
        inID = inID_;
    }
    private XEdge(final XEdge e) {
        id = e.id;
        label = e.label;
        outID = e.outID;
        inID = e.inID;
    }

    private final long id;
    private final String label;
    private final long outID, inID;
}
