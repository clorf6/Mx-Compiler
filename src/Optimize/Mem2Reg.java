package Optimize;

import IR.Block;
import IR.Entity.*;
import IR.Function;
import IR.Program;
import IR.Instruction.*;
import IR.Type.*;

import java.util.*;

public class Mem2Reg {

    public ArrayList<Block> def;
    public HashMap<localVarEntity, Boolean> isAlloca;
    public HashMap<localVarEntity, Entity> replace;
    public Mem2Reg(Program program) {
        this.def = new ArrayList<>();
        this.replace = new HashMap<>();
        this.isAlloca = new HashMap<>();
        for (Function func : program.funcs) {
            new DomtreeBuilder(func);
            for (var ins : func.block.get(0).inst) {
                if (!(ins instanceof alloca)) continue;
                HandleAlloca((alloca) ins, func);
            }
            rename(func.block.get(0));
        }
    }

    public void HandleAlloca(alloca it, Function func) {
        int count = 0;
        def.clear();
        for (Block bl : func.block) {
            for (Instruction ins : bl.inst) {
                if (ins instanceof store now && now.p.equals(it.res)) {
                    def.add(bl);
                }
            }
        }
        //System.out.println(it);
        //System.out.println((localVarEntity) it.res);
        isAlloca.put((localVarEntity) it.res, true);
        Queue<Block> q = new LinkedList<>(def);
        HashMap<Block, Boolean> vis = new HashMap<>();
        HashMap<Block, Boolean> isAdd = new HashMap<>();
        while (!q.isEmpty()) {
            Block now = q.poll();
            for (Block nex : now.DomFrontier) {
                if (!isAdd.containsKey(nex)) {
                    //System.out.println(nex.name + " " + ((localVarEntity) it.res).name);
                    if (Objects.equals(nex.name.name, "return") && !Objects.equals(((localVarEntity) it.res).name, "ret.val")) continue;
                    nex.addPhi(new phi(new localVarEntity(((pointerType) it.res.type).elemType, ((localVarEntity) it.res).name + ++count),
                            (localVarEntity) it.res, new ArrayList<>(), new ArrayList<>(), null));
                    isAdd.put(nex, true);
                    if (!vis.containsKey(nex)) {
                        vis.put(nex, true);
                        q.offer(nex);
                    }
                }
            }
        }
    }

    public Entity getReplace(localVarEntity now) {
        Entity ret = now;
        while (replace.containsKey((localVarEntity) ret)) {
            ret = replace.get((localVarEntity) ret);
            if (!(ret instanceof localVarEntity)) break;
        }
        //replace.put(now, ret);
        return ret;
    }

    public void rename(Block it) {
        HashMap<localVarEntity, Entity> lastReplace = new HashMap<>(replace);
        LinkedList<Instruction> newInst = new LinkedList<>();
        for (phi ins : it.phiInst) {
            replace.put(ins.addr, ins.res);
        }
        for (Instruction ins : it.inst) {
            if (ins instanceof load && ((load) ins).p instanceof localVarEntity && isAlloca.containsKey((localVarEntity) ((load) ins).p)) {
                replace.put((localVarEntity) ((load) ins).res, getReplace((localVarEntity) ((load) ins).p));
            } else if (ins instanceof store && ((store) ins).p instanceof localVarEntity && isAlloca.containsKey((localVarEntity) ((store) ins).p)) {
                if (((store) ins).val instanceof constEntity || ((store) ins).val instanceof globalVarEntity) replace.put((localVarEntity) ((store) ins).p, ((store) ins).val);
                else if (((store) ins).val instanceof localVarEntity) replace.put((localVarEntity) ((store) ins).p, getReplace((localVarEntity) ((store) ins).val));
                //System.out.println(((store) ins).p + " " + ((pointerType) ((store) ins).p.type).getText() + " ? " + replace.get((localVarEntity) ((store) ins).p));
                //if (replace.get((localVarEntity) ((store) ins).p).type instanceof pointerType) System.out.println(((pointerType) replace.get((localVarEntity) (((store) ins).p)).type).getText());

            } else if (!(ins instanceof alloca)) {
                if (ins instanceof binary) {
                    if (((binary) ins).op1 instanceof localVarEntity op1 && replace.containsKey(op1)) ((binary) ins).op1 = replace.get(op1);
                    if (((binary) ins).op2 instanceof localVarEntity op2 && replace.containsKey(op2)) ((binary) ins).op2 = replace.get(op2);
                } else if (ins instanceof icmp) {
                    if (((icmp) ins).op1 instanceof localVarEntity op1 && replace.containsKey(op1)) ((icmp) ins).op1 = replace.get(op1);
                    if (((icmp) ins).op2 instanceof localVarEntity op2 && replace.containsKey(op2)) ((icmp) ins).op2 = replace.get(op2);
                } else if (ins instanceof store) {
                    if (((store) ins).val instanceof localVarEntity val && replace.containsKey(val)) ((store) ins).val = replace.get(val);
                } else if (ins instanceof br) {
                    if (((br) ins).cond instanceof localVarEntity cond && replace.containsKey(cond)) ((br) ins).cond = replace.get(cond);
                } else if (ins instanceof getelementptr) {
                    if (((getelementptr) ins).p instanceof localVarEntity p && replace.containsKey(p)) {
                        //System.out.println(p + " " + replace.get(p));
                        ((getelementptr) ins).p = replace.get(p);
                    }
                    for (int i = 0; i < ((getelementptr) ins).idx.size(); i++) {
                        Entity now = ((getelementptr) ins).idx.get(i);
                        if (now instanceof localVarEntity idx && replace.containsKey(idx)) ((getelementptr) ins).idx.set(i, replace.get(idx));
                    }
                } else if (ins instanceof call) {
                    for (int i = 0; i < ((call) ins).args.size(); i++) {
                        Entity now = ((call) ins).args.get(i);
                        if (now instanceof localVarEntity arg && replace.containsKey(arg)) ((call) ins).args.set(i, replace.get(arg));
                    }
                } else if (ins instanceof ret) {
                    if (((ret) ins).val instanceof localVarEntity val && replace.containsKey(val)) ((ret) ins).val = replace.get(val);
                } else if (ins instanceof phi) {
                    for (int i = 0; i < ((phi) ins).val.size(); i++) {
                        Entity now = ((phi) ins).val.get(i);
                        if (now instanceof localVarEntity Val && replace.containsKey(Val)) ((phi) ins).val.set(i, replace.get(Val));
                    }
                }
                newInst.add(ins);
            }
        }
        it.inst = newInst;
        //System.out.println(it.name.name);
        //System.out.println(newInst);
        for (var nex : it.suc) {
            for (phi ins : nex.phiInst) {
                ins.label.add(it.name);
                if (replace.containsKey(ins.addr)) ins.val.add(replace.get(ins.addr));
                else {
                    if (ins.res.type instanceof pointerType) ins.val.add(new nullEntity());
                    else ins.val.add(new intEntity(0));
                }
            }
        }
        for (var nex : it.tr) rename(nex);
        replace = lastReplace;
    }
}
