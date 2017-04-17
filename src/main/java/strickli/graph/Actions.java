// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.graph;

import lombok.Data;
import strickli.XEdge;
import strickli.XVertex;

public class Actions {
    interface Action {
        void apply(XStore s);
//        void reverse();
//        Action inverse();
    }


    public static Action addVertex(final XVertex v) {
        return new Action() {
            @Override
            public void apply(final XStore s) {
                s.addVertex(v.getRawId(), v);
            }
        };
    }
    public static Action removeVertex(final XVertex v) {
        return new Action() {
            @Override
            public void apply(final XStore s) {
                s.removeVertex(v.getRawId());
            }
        };
    }

    public static Action addEdge(final XEdge e) {
        return new Action() {
            @Override
            public void apply(final XStore s) {
                s.addEdge(e.getRawId(), e);
            }
        };
    }
    public static Action removeEdge(final XEdge e) {
        return new Action() {
            @Override
            public void apply(final XStore s) {
                s.removeEdge(e.getRawId());
            }
        };
    }
}
