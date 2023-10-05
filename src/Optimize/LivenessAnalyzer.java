package Optimize;

import ASM.Block;
import ASM.Function;
import ASM.Entity.*;

import java.util.LinkedHashSet;

public class LivenessAnalyzer {
    Function func;
    LinkedHashSet<Block> vis;
    public LivenessAnalyzer(Function func) {
        this.func = func;
        this.vis = new LinkedHashSet<>();
        getDefUse();
        getRPO(func.block.get(0));
        getInOut();
        getInterval();
    }

    public void getDefUse() {
        for (var block : func.block) {
            block.in.clear();
            block.out.clear();
            block.use.clear();
            block.def.clear();
            for (var inst : block.inst) {
                for (var reg : inst.getUse()) {
                    if (!block.def.contains(reg)) block.use.add(reg);
                }
                block.def.addAll(inst.getDef());
            }
        }
    }

    public void getRPO(Block now) {
        vis.add(now);
        for (Block nex : now.suc) {
            if (!vis.contains(nex) && nex.name.contains("end")) getRPO(nex);
        }
        for (Block nex : now.suc) {
            if (nex.name.contains("end")) continue;
            if (!vis.contains(nex)) getRPO(nex);
        }
        func.RPO.add(now);
        now.pos = func.RPO.size() - 1;
    }

    public void getInOut() {
        boolean flag = true;
        while (flag) {
            flag = false;
            for (var block : func.RPO) {
                LinkedHashSet<virtualReg> newOut = new LinkedHashSet<>();
                for (var v : block.suc) newOut.addAll(v.in);
                LinkedHashSet<virtualReg> newIn = new LinkedHashSet<>(newOut);
                newIn.removeAll(block.def);
                newIn.addAll(block.use);
                if (!newIn.equals(block.in)) {
                    flag = true;
                    block.in = newIn;
                }
                if (!newOut.equals(block.out)) {
                    flag = true;
                    block.out = newOut;
                }
            }
        }
    }

    public void getInterval() {
        int cnt = 0;
        for (int i = func.RPO.size() - 1; i >= 0; i--) {
            Block now = func.RPO.get(i);
            now.beg = cnt;
            for (var ins : now.inst) {
                ins.pos = cnt++;
            }
            now.end = cnt - 1;
            //System.out.println(now.name + " " + now.beg + " " + now.end);
        }
        for (var block : func.RPO) { // for SSA
            for (var reg : block.out) reg.end = Math.max(block.end + 1, reg.end);
            for (var ins : block.inst) {
                for (var reg : ins.getDef()) {
                    reg.beg = Math.min(ins.pos, reg.beg);
                    reg.end = Math.max(ins.pos, reg.end);
                }
                for (var reg : ins.getUse()) reg.end = Math.max(ins.pos, reg.end);
            }
        }
    }

}
