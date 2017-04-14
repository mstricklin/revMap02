package strickli;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import strickli.cache.ImmutableItem;
import strickli.cache.MutableItem;
import strickli.cache.RevCache;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Predicates.in;
import static com.google.common.base.Predicates.not;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

@Slf4j
public class App {
    public static void main( String[] args ) {
        log.info("Log msg");
        RevCache rc = new RevCache();
        RevCache.RevisionCache ri0 = rc.getCacheImmutable(Integer.class, Foo.class);
        ri0.add(17, Foo.of(17, "seventeen"));
        ri0.dump();

//        log.info("");
//        RevCache.RevisionCache ri1 = rc.getCacheImmutable(Integer.class, VertexP.class);
//        VertexP vp0 = new VertexP();
//        ri1.add(17, vp0);
//        MutableVertexP vp1 = new MutableVertexP(vp0);
//        vp1.setProp("aaa", "aaaP");
//        vp1.setProp("bbb", "bbbP");
//        ri1.add(18, vp1);
//
//        ri1.dump();
//
//        vp1.removeProp("aaa");
//        ri1.dump();
        log.info("");
        VP vp = new VP();
        log.info("RevisionCache {}", vp);
        VPMutable vpm = vp.mutableCopy();
        vpm.setProp("aaa-k", "aaa-v");
        vpm.setProp("bbb-k", "bbb-v");
        log.info("RevisionCacheMutable {}", vpm);
        vpm.remove("aaa-k");
        log.info("RevisionCacheMutable {}", vpm);

        VP vp1 = vpm.mergeInto(vp);
        ImmutableItem<VP> vp2 = vpm.merge(vp);


        log.info("Merged {}", vp1);
        List<ImmutableItem<?>> l = newArrayList();
        l.add(vp1);
//        vpm.dump();
    }
    // =================================
    @ToString
    static class VP implements ImmutableItem<VP> {
        protected final Map<String, Object> p;
        VP() {
            p = newHashMap();
        }
        VP(final VP vp) {
            p = newHashMap(vp.p);
        }
        VP(final Map<String, Object> m) {
            p = newHashMap(m);
        }
        @Override
        public VPMutable mutableCopy() {
            return new VPMutable(this);
        }
    }
    @ToString(callSuper=true)
    static class VPMutable extends VP implements MutableItem<VP> {
        VPMutable(VP vp) {
            super(vp);
        }
        void dump() {
            log.info("{}", p);
        }
        void setProp(String k, Object v) {
            revised.put(k, v);
        }
        void remove(String k) {
            revised.remove(k);
            removed.add(k);
        }
        @Override
        public VP mergeInto(VP vp) {
            synchronized (vp) {
                vp.p.putAll(revised);
                for (String k : removed)
                    vp.p.remove(k);
                return vp;
            }
        }
        @Override
        public ImmutableItem<VP> merge(VP vp) {
            Map<String, Object> m = newHashMap(vp.p);
            m.putAll(revised);
            return new VP(Maps.filterKeys(m, not(in(removed))));
        }
        protected final Map<String, Object> revised = newHashMap();
        protected final Set<String> removed = newHashSet();
    }

    private static long VERTEX_ID = 1;
    private static long EDGE_ID = 1;
    // =================================
    @Data(staticConstructor="of")
    private static final class Foo {
        private final long i;
        private final String s;
    }

    // =================================
    @ToString
    static class P {
        P(String p_) {
            p = p_;
            c = new C(p_);
        }
        String p;
        C c;
        void doP() {
            log.info("P.p {}", p);
        }

        class C {
            C(String c_) { c = c_; }
            String c;
            void doC() {
                log.info("P.p {} C.c {}", p, c);
            }
        }
    }

}
