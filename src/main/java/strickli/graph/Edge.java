// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.graph;

public interface Edge {

    Vertex getOutVertex();
    Vertex getInVertex();
    String getLabel();
}
