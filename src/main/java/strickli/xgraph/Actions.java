// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.xgraph;

import lombok.ToString;

public class Actions {
    @ToString
    abstract static class Action {
        abstract Action apply(XStore g);
        abstract Action undo(XStore g);
        public boolean equals(Object object) {
            return false;
        }
    }
    // =================================
    public static Action addVertex(final XVertex v) {
        return new Action() {
            @Override
            public Action apply(final XStore s) {
                s.addVertex(v);
                return this;
            }
            @Override
            public Action undo(final XStore s) {
                s.removeVertex(v);
                return this;
            }
        };
    }
    // =================================
    public static Action removeVertex(final XVertex v) {
        return new Action() {
            @Override
            public Action apply(final XStore s) {
                s.removeVertex(v);
                return this;
            }
            @Override
            Action undo(final XStore s) {
                s.addVertex(v);
                return this;
            }
        };
    }
    // =================================
    public static Action addEdge(final XEdge e) {
        return new Action() {
            @Override
            public Action apply(final XStore s) {
                s.addEdge(e);
                return this;
            }
            @Override
            Action undo(final XStore s) {
                s.removeEdge(e);
                return this;
            }
        };
    }
    // =================================
    public static Action removeEdge(final XEdge e) {
        return new Action() {
            @Override
            public Action apply(final XStore s) {
                s.removeEdge(e);
                return this;
            }
            @Override
            Action undo(final XStore s) {
                // check vertices exist
                s.addEdge(e);
                return this;
            }
        };
    }
}
