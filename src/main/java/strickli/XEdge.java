// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli;

public class XEdge {

    public static XEdge of(long id, XVertex out, XVertex in, String label) {
        return new XEdge(id, label);
    }
    // =================================
    private XEdge(long id_, String label_) {
        id = id_;
        this.label = label_;
    }
    // =================================

    public static XEdge of(long id) {
        return new XEdge(id);
    }
    private XEdge(long id_) {
        id = id_;
    }
    public Object getId() {
        return id;
    }
    public long getRawId() {
        return id;
    }

    private final long id;
    private String label;
}
