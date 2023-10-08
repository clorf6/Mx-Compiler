package Optimize;

import IR.Function;
import IR.Instruction.*;
import IR.Program;
import IR.Entity.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class Global2Local {
    Program program;
    Function inFunc;
    HashSet<Instruction> var = new HashSet<>();
    HashSet<Instruction> use = new HashSet<>();
    ArrayList<Instruction> newGlobalInst = new ArrayList<>();
    public Global2Local(Program program) {
        this.program = program;
        run();
    }

    public void run() {
        for (var ins : program.globalInsts.insts) {
            if (ins instanceof IR.Instruction.global) {
                if (((global) ins).init instanceof constEntity) {
                    globalVarEntity now = ((global) ins).var;
                    boolean change = getUse(now);
                    if (!change) newGlobalInst.add(ins);
                    else if (inFunc != null) {
                        if (Objects.equals(inFunc.name, "main") || Objects.equals(inFunc.name, "_global_init")) {
                            localVarEntity newVar = new localVarEntity(now.type, "global_" + now.name);
                            inFunc.block.get(0).inst.add(0, new alloca(newVar, now.type, null));
                            inFunc.block.get(0).inst.add(1, new store(((global) ins).init, newVar, null));
                            Replace(use, now, newVar);
                        } else newGlobalInst.add(ins);
                    }
                } else newGlobalInst.add(ins);
            } else newGlobalInst.add(ins);
        }
        program.globalInsts.insts = newGlobalInst;
    }

    private boolean getUse(globalVarEntity now) {
        boolean change = true;
        inFunc = null;
        use.clear();
        for (var func : program.funcs) {
            var.clear();
            for (var bl : func.block) {
                for (var inst : bl.inst) {
                    if (inst instanceof binary) {
                        if (((binary) inst).op1 == now) var.add(inst);
                        if (((binary) inst).op2 == now) var.add(inst);
                        if (((binary) inst).res == now) var.add(inst);
                    } else if (inst instanceof br) {
                        if (((br) inst).cond == now) var.add(inst);
                    } else if (inst instanceof getelementptr) {
                        if (((getelementptr) inst).res == now) var.add(inst);
                        if (((getelementptr) inst).p == now) var.add(inst);
                        for (var reg : ((getelementptr) inst).idx) if (reg == now) var.add(inst);
                    } else if (inst instanceof icmp) {
                        if (((icmp) inst).res == now) var.add(inst);
                        if (((icmp) inst).op1 == now) var.add(inst);
                        if (((icmp) inst).op2 == now) var.add(inst);
                    } else if (inst instanceof load) {
                        if (((load) inst).res == now) var.add(inst);
                        if (((load) inst).p == now) var.add(inst);
                    } else if (inst instanceof phi) {
                        if (((phi) inst).res == now) var.add(inst);
                        for (var reg : ((phi) inst).val) if (reg == now) var.add(inst);
                    } else if (inst instanceof ret) {
                        if (((ret) inst).val == now) var.add(inst);
                    } else if (inst instanceof store) {
                        if (((store) inst).p == now) var.add(inst);
                        if (((store) inst).val == now) var.add(inst);
                    } else if (inst instanceof call) {
                        if (((call) inst).res == now) var.add(inst);
                        for (var reg : ((call) inst).args) if (reg == now) var.add(inst);
                    }
                }
            }
            if (!var.isEmpty()) {
                if (inFunc == null) {
                    inFunc = func;
                    use = new HashSet<>(var);
                }
                else {
                    change = false;
                    break;
                }
            }
        }
        return change;
    }

    private void Replace(HashSet<Instruction> use, globalVarEntity now, localVarEntity newVar) {
        for (var inst : use) {
            if (inst instanceof binary) {
                if (((binary) inst).op1 == now) ((binary) inst).op1 = newVar;
                if (((binary) inst).op2 == now) ((binary) inst).op2 = newVar;
                if (((binary) inst).res == now) ((binary) inst).res = newVar;
            } else if (inst instanceof br) {
                if (((br) inst).cond == now) ((br) inst).cond = newVar;
            } else if (inst instanceof getelementptr) {
                if (((getelementptr) inst).res == now) ((getelementptr) inst).res = newVar;
                if (((getelementptr) inst).p == now) ((getelementptr) inst).p = newVar;
                for (var reg : ((getelementptr) inst).idx) if (reg == now) reg = newVar;
            } else if (inst instanceof icmp) {
                if (((icmp) inst).res == now) ((icmp) inst).res = newVar;
                if (((icmp) inst).op1 == now) ((icmp) inst).op1 = newVar;
                if (((icmp) inst).op2 == now) ((icmp) inst).op2 = newVar;
            } else if (inst instanceof load) {
                if (((load) inst).res == now) ((load) inst).res = newVar;
                if (((load) inst).p == now) ((load) inst).p = newVar;
            } else if (inst instanceof phi) {
                if (((phi) inst).res == now) ((phi) inst).res = newVar;
                for (var reg : ((phi) inst).val) if (reg == now) reg = newVar;
            } else if (inst instanceof ret) {
                if (((ret) inst).val == now) ((ret) inst).val = newVar;
            } else if (inst instanceof store) {
                if (((store) inst).p == now) ((store) inst).p = newVar;
                if (((store) inst).val == now) ((store) inst).val = newVar;
            } else if (inst instanceof call) {
                if (((call) inst).res == now) ((call) inst).res = newVar;
                for (var reg : ((call) inst).args) if (reg == now) reg = newVar;
            }
        }
    }
}
