// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli;

import java.util.Collections;

public class XVertex {
    public static XVertex of(long id) {
        return new XVertex(id);
    }
    private XVertex(long id_) {
        id = id_;
    }
    public Object getId() {
        return id;
    }
    public long getRawId() {
        return id;
    }
    public XEdge addEdge(String label, XVertex inVertex) {
        return null;
    }
    public Iterable<XEdge> getEdges() {
        return Collections.emptyList();
    }


    private final long id;
}
