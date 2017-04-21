// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.graph.zzz;

import lombok.ToString;
import strickli.xgraph.XEdge;
import strickli.xgraph.XVertex;

public class Actions {
    @ToString
    abstract static class Action {
        abstract Action apply(Graph g);
        abstract Action undo(Graph g);
        public boolean equals(Object object) {
            return false;
        }
    }
    // =================================
    public static Action addVertex(final XVertex v) {
        return new Action() {
            @Override
            public Action apply(final Graph g) {
                g.addVertex(v);
                return this;
            }
            @Override
            public Action undo(Graph g) {
                g.removeVertex(v);
                return this;
            }
        };
    }
    // =================================
    public static Action removeVertex(final XVertex v) {
        return new Action() {
            @Override
            public Action apply(final Graph g) {
                g.removeVertex(v);
                return this;
            }
            @Override
            Action undo(Graph g) {
                g.addVertex(v);
                return this;
            }
        };
    }
    // =================================
    public static Action addEdge(final XEdge e) {
        return new Action() {
            @Override
            public Action apply(final Graph g) {
                // check vertices exist
                g.addEdge(e);
                return this;
            }
            @Override
            Action undo(Graph g) {
                g.removeEdge(e);
                return this;
            }
        };
    }
    // =================================
    public static Action removeEdge(final XEdge e) {
        return new Action() {
            @Override
            public Action apply(final Graph g) {
                g.removeEdge(e);
                return this;
            }
            @Override
            Action undo(Graph g) {
                // check vertices exist
                g.addEdge(e);
                return this;
            }
        };
    }
}
