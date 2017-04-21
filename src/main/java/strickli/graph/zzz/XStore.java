// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.graph.zzz;

import strickli.xgraph.XEdge;
import strickli.xgraph.XVertex;

public interface XStore {
    void addVertex(Long id, XVertex v);
    void removeVertex(Long vID);
    void addEdge(Long id, XEdge v);
    void removeEdge(Long id);
}
