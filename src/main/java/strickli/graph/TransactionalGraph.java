// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.graph;

public interface TransactionalGraph {

    void commit();
    void rollback();
}
