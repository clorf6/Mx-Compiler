package Optimize;

import ASM.Block;
import ASM.Function;
import ASM.Entity.*;
import Utils.Position;

import java.util.*;

public class LivenessAnalyzer {
    Function func;
    HashSet<Block> vis;
    public LivenessAnalyzer(Function func) {
        this.func = func;
        this.vis = new HashSet<>();
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

    public void getRPO(Block u) {
        Stack<Block> slot = new Stack<>();
        vis.add(u);
        slot.push(u);
        while (!slot.isEmpty()) {
            Block now = slot.peek();
            boolean flag = true;
            for (Block nex : now.suc) {
                if (!vis.contains(nex)) {
                    vis.add(nex);
                    slot.push(nex);
                    flag = false;
                    break;
                }
            }
            if (flag) {
                func.RPO.add(now);
                slot.pop();
            }
        }
        Collections.reverse(func.RPO);
    }

    public void getInOut() {
        boolean flag = true;
        while (flag) {
            flag = false;
            for (int i = func.RPO.size() - 1; i >= 0; i--) {
                var block = func.RPO.get(i);
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
        for (var block : func.RPO) {
            for (var ins : block.inst) {
                ins.pos = cnt;
                for (var reg : ins.getDef()) {
                    reg.beg = Math.min(cnt, reg.beg);
                    reg.end = Math.max(cnt, reg.end);
                }
                for (var reg : ins.getUse()) reg.end = Math.max(cnt, reg.end);
                cnt++;
            }
            cnt++;
            for (var reg : block.out) reg.end = Math.max(cnt, reg.end);
            //System.out.println(now.name + " " + now.beg + " " + now.end);
        }
    }

}