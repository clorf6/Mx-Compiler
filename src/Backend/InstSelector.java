package Backend;

import ASM.Asciz;
import ASM.Entity.Entity;
import ASM.Instruction.*;
import ASM.Word;
import IR.*;
import IR.Entity.*;
import IR.Type.*;
import ASM.Program;
import ASM.Entity.*;
import IR.Instruction.binary;
import IR.Type.pointerType;
import Utils.Scope.globalScope;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class InstSelector implements IRVisitor {

    Program program;
    ASM.Function currentFunction;

    globalScope global;
    ASM.Block currentBlock;
    HashMap<String, reg> regs;
    HashMap<String, ASM.Block> blocks;
    HashSet<String> isAlloca;
    int count = 0;

    public InstSelector(Program program, globalScope global) {
        this.program = program;
        this.regs = new HashMap<>();
        this.blocks = new HashMap<>();
        this.isAlloca = new HashSet<>();
        this.global = global;
    }

    public virtualReg getTempReg(int size) {
        return new virtualReg("var" + ++count, size);
    }

    public virtualReg storeImm(imm val, int size) {
        virtualReg tmp = getTempReg(size);
        currentBlock.add(new li(tmp, val));
        return tmp;
    }
    public Entity convertReg(IR.Entity.Entity now) {
        if (now instanceof intEntity) {
            int val = ((intEntity) now).val;
            Entity ret = new imm(val);
            if (val < -2048 || val >= 2048) ret = storeImm((imm) ret, 4);
            return ret;
        } else if (now instanceof boolEntity) {
            return new imm(((boolEntity) now).val ? 1 : 0);
        } else if (now instanceof nullEntity) {
            return new imm(0);
        } else if (now instanceof globalVarEntity) {
            virtualReg ret = getTempReg(now.type.size());
            currentBlock.add(new la(ret, ((globalVarEntity) now).name));
            return ret;
        } else {
            reg ret = regs.get(currentFunction.name + "."  + now.toString());
            if (ret == null) {
                ret = getTempReg(now.type.size());
                regs.put(currentFunction.name + "."  + now, ret);
            }
            return ret;
        }
    }

    @Override
    public void visit(IR.Program it) {
        it.globalInsts.accept(this);
        it.funcs.forEach(func -> func.accept(this));
        program.merge();
    }

    @Override
    public void visit(IR.Global it) {
        it.insts.forEach(inst -> inst.accept(this));
    }

    @Override
    public void visit(IR.Function it) {
        currentFunction = new ASM.Function(it.name);
        currentFunction.callSize = 0;
        currentBlock = new ASM.Block(currentFunction.name);
        blocks.put(currentBlock.name, currentBlock);
        for (int i = 1; i < it.block.size(); i++) {
            String blockName = currentFunction.name + "." + it.block.get(i).name.name;
            blocks.put(blockName, new ASM.Block(blockName));
        }
        program.text.globl.add(currentFunction.name);
        for (int i = 0; i < Integer.min(8, it.param.size()); i++) {
            program.a(i).size = it.param.get(i).type.size();
            virtualReg tmp = getTempReg(it.param.get(i).type.size());
            currentBlock.add(new mv(tmp, program.a(i)));
            regs.put(currentFunction.name + "."  + it.param.get(i).toString(), tmp);
        }
        for (int i = 8; i < it.param.size(); i++) {
            virtualReg tmp = getTempReg(it.param.get(i).type.size());
            currentBlock.add(new ASM.Instruction.load(tmp,
                    new memory(program.s(0), new imm((i - 8) * 4), it.param.get(i).type.size())));
            regs.put(currentFunction.name + "."  + it.param.get(i).toString(), tmp);
        }
        it.block.get(0).accept(this);
        currentFunction.block.add(currentBlock);
        for (int i = 1; i < it.block.size(); i++) {
            currentBlock = blocks.get(currentFunction.name + "." + it.block.get(i).name.name);
            it.block.get(i).accept(this);
            currentFunction.block.add(currentBlock);
        }
        program.text.func.add(currentFunction);
    }

    @Override
    public void visit(IR.Block it) {
        it.inst.forEach(ins -> ins.accept(this));
    }

    @Override
    public void visit(IR.Instruction.binary it) {
        reg res = (reg) convertReg(it.res);
        //currentFunction.size += it.res.type.size();
        Entity op1 = convertReg(it.op1), op2 = convertReg(it.op2);
        if (it.op == binary.binaryOp.sub || it.op == binary.binaryOp.mul || it.op == binary.binaryOp.sdiv || it.op == binary.binaryOp.srem) {
            if (op1 instanceof imm) op1 = storeImm((imm) op1, it.op1.type.size());
            if (op2 instanceof imm) op2 = storeImm((imm) op2, it.op2.type.size());
            currentBlock.add(new ASM.Instruction.binary(res, op1, it.op, op2, false));
        } else {
            if (op1 instanceof imm) op1 = storeImm((imm) op1, it.op1.type.size());
            currentBlock.add(new ASM.Instruction.binary(res, op1, it.op, op2, op2 instanceof imm));
        }
    }

    public void addedge(ASM.Block now, ASM.Block nex) {
        now.suc.add(nex);
        nex.pre.add(now);
    }

    @Override
    public void visit(IR.Instruction.br it) {
        ASM.Block nex;
        if (it.cond != null) {
            Entity cond = convertReg(it.cond);
            nex = blocks.get(currentFunction.name + "." + it.iffalse.name);
            addedge(currentBlock, nex);
            currentBlock.jumpInst.add(new ASM.Instruction.br("beqz", cond, currentFunction.name + "." + it.iffalse.name));
        }
        nex = blocks.get(currentFunction.name + "." + it.iftrue.name);
        addedge(currentBlock, nex);
        currentBlock.jumpInst.add(new j(currentFunction.name + "." + it.iftrue.name));
    }

    @Override
    public void visit(IR.Instruction.ret it) {
        currentFunction.retBlock = currentBlock;
        if (!(it.val.type instanceof voidType)) {
            Entity val = convertReg(it.val);
            if (val instanceof imm) currentBlock.add(new li(program.a(0), (imm) val));
            else currentBlock.add(new mv(program.a(0), (reg) val));
        }
        currentBlock.add(new ASM.Instruction.ret());
    }

    @Override
    public void visit(IR.Instruction.alloca it) {
        virtualReg tmp = getTempReg(it.type.size());
        regs.put(currentFunction.name + "."  + it.res.toString(), tmp);
        isAlloca.add(((varEntity) it.res).name);
    }

    @Override
    public void visit(IR.Instruction.load it) {
        reg res = (reg) convertReg(it.res);
        //currentFunction.size += it.res.type.size();
        Entity pos = convertReg(it.p);
        if (isAlloca.contains(((varEntity) it.p).name)) currentBlock.add(new mv(res, (reg) pos));
        else currentBlock.add(new ASM.Instruction.load(res, new memory((reg) pos, new imm(0), res.size)));
    }

    @Override
    public void visit(IR.Instruction.store it) {
        Entity val = convertReg(it.val);
        if (val instanceof imm) val = storeImm((imm) val, it.val.type.size());
        Entity pos = convertReg(it.p);
        if (isAlloca.contains(((varEntity) it.p).name)) currentBlock.add(new mv((reg) pos, (reg) val));
        else currentBlock.add(new ASM.Instruction.store((reg) val, new memory((reg) pos, new imm(0), ((reg) val).size)));
    }

    @Override
    public void visit(IR.Instruction.global it) {
        if (it.init instanceof intEntity) {
            program.data.word.add(new Word(it.var.name, Integer.toString(((intEntity) it.init).val), false));
        } else if (it.init instanceof boolEntity) {
            program.data.word.add(new Word(it.var.name, ((boolEntity) it.init).val ? "1" : "0", true));
        } else if (it.init instanceof nullEntity) {
            program.data.word.add(new Word(it.var.name, "0", false));
        } else {
            program.data.word.add(new Word(it.var.name, ((varEntity) it.init).name, false));
        }
    }

    @Override
    public void visit(IR.Instruction.constant it) {
        String val = ((stringEntity) it.init).val.replace("\\0A", "\\n")
                .replace("\\22", "\\\"");
        program.rodata.str.add(new Asciz(it.var.name, "\"" + val + "\""));
    }

    @Override
    public void visit(IR.Instruction.bitcast it) {

    }

    @Override
    public void visit(IR.Instruction.declare it) {

    }

    @Override
    public void visit(IR.Instruction.classdef it) {

    }

    @Override
    public void visit(IR.Instruction.getelementptr it) {
        //currentFunction.size += it.res.type.size();
        reg res;
        reg p = (reg) convertReg(it.p);
        res = (reg) convertReg(it.res);
        if (it.idx.size() == 1) {
            if (it.idx.get(0) instanceof intEntity) {
                res = (reg) convertReg(it.res);
                int val = ((intEntity) it.idx.get(0)).val * ((pointerType) it.p.type).elemType.size();
                Entity tmp = new imm(val);
                if (val < -2048 || val >= 2048) tmp = storeImm((imm) tmp, 4);
                currentBlock.add(new ASM.Instruction.binary(res, p, binary.binaryOp.add, tmp, tmp instanceof imm));
            } else {
                Entity idx = convertReg(it.idx.get(0));
                if (((pointerType) it.p.type).elemType.size() == 1) currentBlock.add(new ASM.Instruction.mv(res, (reg) idx));
                else currentBlock.add(new ASM.Instruction.binary(res, idx, "slli",
                            new imm(2)));
                regs.put(currentFunction.name + "."  + it.res.toString(), res);
                currentBlock.add(new ASM.Instruction.binary(res, p, "add", res));
            }
        } else {
            res = (reg) convertReg(it.res);
            classType Class = (classType) ((pointerType) it.p.type).elemType;
            Class = global.getClass(Class.name);
            int pos = Class.offset.get(((intEntity) it.idx.get(1)).val) * 4;
            Entity tmp = new imm(pos);
            if (pos < -2048 || pos >= 2048) tmp = storeImm((imm) tmp, 4);
            currentBlock.add(new ASM.Instruction.binary(res, p, binary.binaryOp.add, tmp, tmp instanceof imm));
        }
    }

    @Override
    public void visit(IR.Instruction.icmp it) {
        reg res = (reg) convertReg(it.res);
        //currentFunction.size += it.res.type.size();
        Entity op1 = convertReg(it.op1), op2 = convertReg(it.op2);
        if (op1 instanceof imm) op1 = storeImm((imm) op1, it.op1.type.size());
        if (op2 instanceof imm) {
            if (((imm) op2).val < -2048 || ((imm) op2).val >= 2048) op2 = storeImm((imm) op2, 4);
        }
        switch (it.op) {
            case eq -> {
                reg tmp = getTempReg(((reg) op1).size);
                currentBlock.add(new ASM.Instruction.binary(tmp, op1, binary.binaryOp.xor,
                        op2, op2 instanceof imm));
                currentBlock.add(new comp("seqz", res, tmp));
            }
            case ne -> {
                reg tmp = getTempReg(((reg) op1).size);
                currentBlock.add(new ASM.Instruction.binary(tmp, op1, binary.binaryOp.xor,
                        op2, op2 instanceof imm));
                currentBlock.add(new comp("snez", res, tmp));
            }
            case slt -> currentBlock.add(new ASM.Instruction.binary(res, op1, "slt" + (op2 instanceof imm ? "i" : ""), op2));
            case sgt -> {
                if (op2 instanceof imm) {
                    currentBlock.add(new ASM.Instruction.binary(res, storeImm((imm) op2, 4), "slt" , op1));
                } else currentBlock.add(new ASM.Instruction.binary(res, op2, "slt" , op1));
            }
            case sle -> {
                if (op2 instanceof imm) {
                    currentBlock.add(new ASM.Instruction.binary(res, storeImm((imm) op2, 4), "slt" , op1));
                } else currentBlock.add(new ASM.Instruction.binary(res, op2, "slt", op1));
                currentBlock.add(new ASM.Instruction.binary(res, res, "xori", new imm(1)));
            }
            case sge -> {
                currentBlock.add(new ASM.Instruction.binary(res, op1, "slt" + (op2 instanceof imm ? "i" : ""), op2));
                currentBlock.add(new ASM.Instruction.binary(res, res, "xori", new imm(1)));
            }
        }
    }

    @Override
    public void visit(IR.Instruction.call it) {
        for (int i = 0; i < Integer.min(8, it.args.size()); ++i) {
            Entity tmp = convertReg(it.args.get(i));
            if (tmp instanceof reg) currentBlock.add(new mv(program.a(i), (reg) tmp));
            else if (tmp instanceof imm) currentBlock.add(new li(program.a(i), (imm) tmp));
        }
        int callsize = 0;
        for (int i = 8; i < it.args.size(); i++) {
            Entity tmp = convertReg(it.args.get(i));
            if (tmp instanceof imm) tmp = storeImm((imm) tmp, 4);
            currentBlock.add(new ASM.Instruction.store((reg) tmp, new memory(program.sp, new imm((i - 8) * 4), ((reg) tmp).size)));
            callsize += ((reg) tmp).size;
        }
        currentFunction.callSize = Integer.max(currentFunction.callSize, callsize);
        currentBlock.add(new ASM.Instruction.call(it.name, program));
        if (it.res != null) {
            reg res = (reg) convertReg(it.res);
            //currentFunction.size += it.res.type.size();
            currentBlock.add(new mv(res, program.a(0)));
        }
    }

    @Override
    public void visit(IR.Instruction.phi it) {
        reg res = getTempReg(it.res.type.size());
        reg now = (reg) convertReg(it.res);
        currentBlock.add(new mv(now, res));
        //currentFunction.size += it.res.type.size();
        for (int i = 0; i < it.label.size(); i++) {
            ASM.Block nowBlock;
            if (Objects.equals(it.label.get(i).name, "entry")) nowBlock = blocks.get(currentFunction.name);
            else nowBlock = blocks.get(currentFunction.name + "." + it.label.get(i).name);
            if (it.val.get(i) instanceof intEntity) nowBlock.phiInst.add(new li(res, new imm(((intEntity) it.val.get(i)).val)));
            else if (it.val.get(i) instanceof boolEntity) nowBlock.phiInst.add(new li(res, new imm(((boolEntity) it.val.get(i)).val ? 1 : 0)));
            else if (it.val.get(i) instanceof globalVarEntity) nowBlock.phiInst.add(new la(res, ((globalVarEntity) it.val.get(i)).name));
            else {
                Entity ret = convertReg(it.val.get(i));
                if (ret instanceof reg) nowBlock.phiInst.add(new mv(res, (reg) ret));
                else if (ret instanceof imm) nowBlock.phiInst.add(new li(res, (imm) ret));
            }

        }
    }
}
