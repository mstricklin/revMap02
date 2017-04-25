// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.xgraph;

public interface XStore {
    void addVertex(XVertex v);
    XVertex.Mutable getVertex(Long id);
    void removeVertex(XVertex v);

//    void addOutEdge(XVertex v, XEdge e);
//    void rmOutEdge(XVertex v, XEdge e);

    void addEdge(XEdge e);
    XEdge getEdge(Long id);
    void removeEdge(XEdge e);

    void dump();
}
