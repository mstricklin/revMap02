// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.graph;

import strickli.XEdge;
import strickli.XVertex;


public interface Graph {

    XVertex addVertex(XVertex v);
    XVertex getVertex(Object id);
    XVertex removeVertex(XVertex v);
    XEdge addEdge(XEdge e);
    XEdge getXEdge(Object id);
    XEdge removeEdge(XEdge e);
}
