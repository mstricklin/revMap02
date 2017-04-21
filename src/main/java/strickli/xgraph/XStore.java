// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.xgraph;

public interface XStore {
    void addVertex(XVertex v);
    XVertex getVertex(Long id);
    void removeVertex(Long id);

    void addOutEdge(XVertex v, XEdge e);
    void rmOutEdge(XVertex v, XEdge e);

    void addEdge(XEdge e);
    XEdge getEdge(Long id);
    void removeEdge(Long id);

    void dump();
}
