// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.xgraph;

import static com.google.common.collect.Queues.newArrayDeque;
import static strickli.xgraph.RevCache.newRevCache;

import java.util.Queue;

public class YStore {

    private ThreadLocal<TransactionWork2> tx2 = new ThreadLocal<TransactionWork2>() {
        @Override
        protected TransactionWork2 initialValue() {
            return new TransactionWork2( YStore.this );
        }
    };
    private static class TransactionWork2 {
        private final XCache<XVertex.RawVertex> vRevision;
        private final Queue<Actions.Action2> actions = newArrayDeque();
        TransactionWork2(YStore s) {
            vRevision = s.vBaseline.getRevision();
        }
        private Actions.Action2 applyAndQueue(Actions.Action2 a) {
            actions.add( a.apply( vRevision ) );
            return a;
        }
    }

    private final RevCache<XVertex.RawVertex> vBaseline = newRevCache();
    private final RevCache<XEdge.RawEdge> eBaseline = newRevCache();
}
