// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.graph;

public interface Graph {
    Vertex addVertex(Object id);
    Vertex getVertex(Object id);
    void removeVertex(Vertex v);

    Edge addEdge(Object id, Vertex outVertex, Vertex inVertex, String label);
    Edge getEdge(Object id);
    void removeEdge(Edge v);

    void dump();

    public void shutdown();

}
