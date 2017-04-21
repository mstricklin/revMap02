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
                s.removeVertex(v.getRawId());
                return this;
            }
        };
    }
    // =================================
    public static Action removeVertex(final XVertex v) {
        return new Action() {
            @Override
            public Action apply(final XStore s) {
                s.removeVertex(v.getRawId());
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
    public static Action addOutEdge(final XVertex v, final XEdge e) {
        return new Action() {
            @Override
            public Action apply(final XStore s) {
                s.addOutEdge(v, e);
                return this;
            }
            @Override
            public Action undo(final XStore s) {
                s.rmOutEdge( v, e);
                return this;
            }
        };
    }
    // =================================
    public static Action addEdge(final XEdge e) {
        return new Action() {
            @Override
            public Action apply(final XStore s) {
                // check vertices exist
                s.addEdge(e);
                return this;
            }
            @Override
            Action undo(final XStore s) {
                s.removeEdge(e.getRawId());
                return this;
            }
        };
    }
    // =================================
    public static Action removeEdge(final XEdge e) {
        return new Action() {
            @Override
            public Action apply(final XStore s) {
                s.removeEdge(e.getRawId());
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