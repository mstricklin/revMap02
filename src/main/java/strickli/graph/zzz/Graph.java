// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.graph.zzz;

import strickli.xgraph.XEdge;
import strickli.xgraph.XVertex;


public interface Graph {

    XVertex addVertex(XVertex v);
    XVertex getVertex(Object id);
    XVertex removeVertex(XVertex v);
    XEdge addEdge(XEdge e);
    XEdge getEdge(Object id);
    XEdge removeEdge(XEdge e);

    void dump();
}
