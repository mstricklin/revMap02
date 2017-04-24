// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.graph;

public interface Vertex {

    Iterable<Edge> getEdges(String... labels);
    Iterable<Vertex> getVertices(String... labels);

    Iterable<Edge> getOutEdges(String... labels);
    Iterable<Edge> getInEdges(String... labels);

    Iterable<Vertex> getOutVertices(String... labels);
    Iterable<Vertex> getInVertices(String... labels);

    Edge addEdge(String label, Vertex inVertex);
}
