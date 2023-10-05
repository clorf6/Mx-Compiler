package Optimize;

import IR.Block;
import IR.Function;

import java.util.HashMap;
import java.util.Objects;
import java.util.Stack;

public class
DomtreeBuilder {
    public int tot;
    public HashMap<Integer, Block> id;

    public DomtreeBuilder(Function func) {
        id = new HashMap<>();
        tot = 0;
        dfs(func.block.get(0));
        for (Block now : func.block) {
            now.semi = now;
            now.fa = now;
            now.minn = now;
        }
        LengauerTarjan();
        getDomFrontier();
    }

    public Block getfa(Block x) {
        if (Objects.equals(x.fa, x)) return x;
        Block p = getfa(x.fa);
        if (x.fa.minn.semi.dfn < x.minn.semi.dfn) {
            x.minn = x.fa.minn;
        }
        return x.fa = p;
    }

    public void dfs(Block now) {
        Stack<Block> slot = new Stack<>();
        slot.push(now);
        while (!slot.isEmpty()) {
            Block u = slot.pop();
            u.dfn = ++tot;
            id.put(tot, u);
            for (Block v : u.suc) {
                if (v.dfn != 0) continue;
                v.anc = u;
                slot.push(v);
            }
        }
    }

    void LengauerTarjan() {
        for (int i = tot; i >= 2; i--) {
            Block u = id.get(i);
            for (Block v : u.pre) {
                if (v.dfn == 0) continue;
                getfa(v);
                if (v.minn.semi.dfn < u.semi.dfn) {
                    u.semi = v.minn.semi;
                }
            }
            u.semi.tr.add(u);
            u = u.fa = u.anc;
            for (Block v : u.tr) {
                getfa(v);
                v.idom = (Objects.equals(u.name, v.minn.semi.name))?u:v.minn;
            }
            u.tr.clear();
        }
        for (int i = 2; i <= tot; i++) {
            Block u = id.get(i);
            u.tr.clear();
            if (!Objects.equals(u.semi.name, u.idom.name)) {
                u.idom = u.idom.idom;
            }
        }
        for (int i = 2; i <= tot; i++) {
            Block u = id.get(i);
            u.idom.tr.add(u);
        }
    }

    void getDomFrontier() {
        for (int i = 1; i <= tot; i++) {
            Block now = id.get(i);
            if (now.pre.size() > 1) {
                for (Block p : now.pre) {
                    while (p != null && !Objects.equals(p.name, now.idom.name)) {
                        p.DomFrontier.add(now);
                        p = p.idom;
                    }
                }
            }
        }
    }
}
