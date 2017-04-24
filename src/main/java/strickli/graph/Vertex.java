// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.graph;

public interface Vertex {

    Iterable<Edge> getEdges(Direction direction, String... labels);
    Iterable<Vertex> getVertices(Direction direction, String... labels);

    // VertexQuery query();

    Edge addEdge(String label, Vertex inVertex);
}
