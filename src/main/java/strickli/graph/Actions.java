// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.graph;

import com.google.common.base.Function;
import strickli.XEdge;
import strickli.XVertex;

public class Actions {
    abstract static class Action {
        abstract void apply(Graph g);
        abstract void undo(Graph g);
        public boolean equals(Object object) {
            return false;
        }
    }
    // =================================
    public static Action addVertex(final XVertex v) {
        return new Action() {
            @Override
            public void apply(final Graph g) {
                g.addVertex(v);
            }
            @Override
            public void undo(Graph g) {
                g.removeVertex(v);
            }
        };
    }
    // =================================
    public static Action removeVertex(final XVertex v) {
        return new Action() {
            @Override
            public void apply(final Graph g) {
                g.removeVertex(v);
            }
            @Override
            void undo(Graph g) {
                g.addVertex(v);

            }
        };
    }
    // =================================
    public static Action addEdge(final XEdge e) {
        return new Action() {
            @Override
            public void apply(final Graph g) {
                g.addEdge(e);
            }
            @Override
            void undo(Graph g) {
                g.removeEdge(e);
            }
        };
    }
    // =================================
    public static Action removeEdge(final XEdge e) {
        return new Action() {
            @Override
            public void apply(final Graph g) {
                g.removeEdge(e);
            }
            @Override
            void undo(Graph g) {
                g.addEdge(e);
            }
        };
    }
}
