package Backend;

import ASM.Asciz;
import ASM.Entity.Entity;
import ASM.Instruction.*;
import ASM.Word;
import IR.*;
import IR.Entity.*;
import IR.Type.*;
import IR.Instruction.*;
import ASM.Program;
import ASM.Entity.*;
import IR.Instruction.binary;
import IR.Instruction.br;
import IR.Instruction.call;
import IR.Instruction.load;
import IR.Instruction.ret;
import IR.Instruction.store;
import IR.Type.pointerType;
import Utils.Scope.globalScope;

import java.util.HashMap;
import java.util.Objects;

public class ASMBuilder implements IRVisitor {

    Program program;
    Function currentFunction;

    globalScope global;
    ASM.Block currentBlock, retBlock;
    HashMap<String, Entity> regs;
    HashMap<String, ASM.Block> blocks;
    HashMap<String, Boolean> isAlloca;

    public ASMBuilder(Program program, globalScope global) {
        this.program = program;
        this.regs = new HashMap<>();
        this.blocks = new HashMap<>();
        this.isAlloca = new HashMap<>();
        this.global = global;
    }

    @Override
    public void visit(IR.Program it) {
        it.globalInsts.accept(this);
        it.funcs.forEach(func -> func.accept(this));
    }

    @Override
    public void visit(IR.Global it) {
        it.insts.forEach(inst -> inst.accept(this));
    }

    @Override
    public void visit(IR.Function it) {
        currentFunction = it;
        currentFunction.size = 8;
        currentFunction.callSize = 0;
        ASM.Block fir;
        fir = currentBlock = new ASM.Block(currentFunction.name);
        blocks.put(currentBlock.name, currentBlock);
        program.text.globl.add(currentFunction.name);
        for (int i = 0; i < Integer.min(8, it.param.size()); i++) {
            currentFunction.size += it.param.get(i).type.size();
            program.a(i).size = it.param.get(i).type.size();
            memory tmp = new memory(program.s(0), new imm(-currentFunction.size), program.a(i).size);
            currentBlock.add(new ASM.Instruction.store(program.a(i), tmp));
            regs.put(currentFunction.name + "."  + it.param.get(i).toString(), tmp);
        }
        for (int i = 8; i < it.param.size(); i++) {
            regs.put(currentFunction.name + "."  + it.param.get(i).toString(),
                    new memory(program.s(0), new imm((i - 8) * 4), it.param.get(i).type.size()));
        }
        it.block.get(0).accept(this);
        program.text.block.add(currentBlock);
        for (int i = 1; i < it.block.size(); i++) {
            currentBlock = new ASM.Block(currentFunction.name + "." + it.block.get(i).name.name);
            blocks.put(currentBlock.name, currentBlock);
            it.block.get(i).accept(this);
            program.text.block.add(currentBlock);
        }
        currentFunction.size += currentFunction.callSize;
        int size = (currentFunction.size / 16 + ((currentFunction.size % 16 != 0) ? 1 : 0)) * 16;
        Entity st = getTempReg(4);
        reg tmp = getTempReg(4);
        fir.inst.add(0, new ASM.Instruction.store(tmp, new memory(program.s(0), new imm(-8), 4)));
        fir.inst.add(0, new ASM.Instruction.store(program.ra, new memory(program.s(0), new imm(-4), 4)));
        fir.inst.add(0, new ASM.Instruction.binary(program.sp, program.sp,
                binary.binaryOp.sub, st, false));
        fir.inst.add(0, new mv(program.s(0), program.sp));
        fir.inst.add(0, new mv(tmp, program.s(0)));
        fir.inst.add(0, new li((reg) st, new imm(size)));
        st = new imm(size);
        if (size < -2048 || size >= 2048) {
            st = getTempReg(4);
            retBlock.inst.add(retBlock.inst.size() - 1, new li((reg) st, new imm(size)));
        }
        retBlock.inst.add(retBlock.inst.size() - 1, new ASM.Instruction.binary(program.sp, program.sp, binary.binaryOp.add, st, st instanceof imm));
        retBlock.inst.add(retBlock.inst.size() - 1, new ASM.Instruction.load(program.s(0), new memory(program.sp, new imm(-8), 4)));
        retBlock.inst.add(retBlock.inst.size() - 1, new ASM.Instruction.load(program.ra, new memory(program.sp, new imm(-4), 4)));
    }

    @Override
    public void visit(IR.Block it) {
//        it.phiInst.forEach(ins -> {
//            freeRegCount = 0;
//            ins.accept(this);
//        });
        it.inst.forEach(ins -> {
            freeRegCount = 0;
            ins.accept(this);
        });
    }

    @Override
    public void visit(IR.Instruction.binary it) {
        reg res;
        Entity convertedRes = convertReg(it.res, false);
        if (convertedRes instanceof memory) res = getTempReg(it.res.type.size());
        else res = (reg) convertedRes;
        Entity op1 = convertReg(it.op1, true), op2 = convertReg(it.op2, true);
        if (it.op == binary.binaryOp.sub || it.op == binary.binaryOp.mul || it.op == binary.binaryOp.sdiv || it.op == binary.binaryOp.srem) {
            if (op1 instanceof imm) op1 = storeImm((imm) op1, it.op1.type.size());
            if (op2 instanceof imm) op2 = storeImm((imm) op2, it.op2.type.size());
            currentBlock.add(new ASM.Instruction.binary(res, op1, it.op, op2, false));
        } else {
            if (op1 instanceof imm) op1 = storeImm((imm) op1, it.op1.type.size());
            if (op2 instanceof imm && (((imm) op2).val < -2048 || ((imm) op2).val >= 2048)) op2 = storeImm((imm) op2, 4);
            currentBlock.add(new ASM.Instruction.binary(res, op1, it.op, op2, op2 instanceof imm));
        }
        if (convertedRes instanceof memory) {
            currentBlock.add(new ASM.Instruction.store(res, updateMem((memory) convertedRes)));
        }
    }

    @Override
    public void visit(IR.Instruction.br it) {
        if (it.cond != null) {
            Entity cond = convertReg(it.cond, true);
            currentBlock.add(new ASM.Instruction.br("beqz", cond, currentFunction.name + "." + it.iffalse.name));
        }
        currentBlock.add(new j(currentFunction.name + "." + it.iftrue.name));
    }

    @Override
    public void visit(IR.Instruction.ret it) {
        retBlock = currentBlock;
        if (!(it.val.type instanceof voidType)) {
            Entity val = convertReg(it.val, true);
            if (val instanceof imm) currentBlock.add(new li(program.a(0), (imm) val));
            else currentBlock.add(new mv(program.a(0), (reg) val));
        }
        currentBlock.add(new ASM.Instruction.ret());
    }

    @Override
    public void visit(IR.Instruction.alloca it) {
        //System.out.println(((varEntity) it.res).name);
        currentFunction.size += it.type.size();
        //System.out.println(currentFunction.size);
        memory pos = new memory(program.s(0), new imm(-currentFunction.size), it.type.size());
        regs.put(currentFunction.name + "."  + it.res.toString(), pos);
        isAlloca.put(((varEntity) it.res).name, true);
    }

    @Override
    public void visit(IR.Instruction.load it) {
        reg res;
        Entity convertedRes = convertReg(it.res, false);
        if (convertedRes instanceof memory) res = getTempReg(it.res.type.size());
        else res = (reg) convertedRes;
        Entity pos = convertReg(it.p, false);
        if (pos instanceof memory) {
            if (isAlloca.get(((varEntity) it.p).name) != null) {
                currentBlock.add(new ASM.Instruction.load(res, (memory) pos));
            } else {
                currentBlock.add(new ASM.Instruction.load(res, new memory(loadMem((memory) pos), new imm(0), res.size)));
            }
        }
        else currentBlock.add(new ASM.Instruction.load(res, new memory((physicReg) pos, new imm(0), res.size)));
        if (convertedRes instanceof memory) {
            currentBlock.add(new ASM.Instruction.store(res, updateMem((memory) convertedRes)));
        }
    }

    @Override
    public void visit(IR.Instruction.store it) {
        reg res;
        Entity convertedRes = convertReg(it.val, false);
        if (convertedRes instanceof imm) res = storeImm((imm) convertedRes, it.val.type.size());
        else if (convertedRes instanceof memory) res = loadMem((memory) convertedRes);
        else res = (reg) convertedRes;
        Entity pos = convertReg(it.p, false);
        if (pos instanceof memory) {
            if (isAlloca.get(((varEntity) it.p).name) != null) {
                currentBlock.add(new ASM.Instruction.store(res, (memory) pos));
            } else {
                currentBlock.add(new ASM.Instruction.store(res, new memory(loadMem((memory) pos), new imm(0), res.size)));
            }
        }
        else currentBlock.add(new ASM.Instruction.store(res, new memory((physicReg) pos, new imm(0), res.size)));
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
        reg res;
        Entity convertedRes = convertReg(it.res, false);
        if (convertedRes instanceof memory) {
            res = getTempReg(it.res.type.size());
        } else res = (reg) convertedRes;
        Entity p = convertReg(it.p, true);
        if (it.idx.size() == 1) {
            if (it.idx.get(0) instanceof intEntity) {
                int val = ((intEntity) it.idx.get(0)).val * ((pointerType) it.p.type).elemType.size();
                Entity tmp = new imm(val);
                if (val < -2048 || val >= 2048) tmp = storeImm((imm) tmp, 4);
                if (val != 0) currentBlock.add(new ASM.Instruction.binary(res, p, binary.binaryOp.add, tmp, tmp instanceof imm));
                else res = (reg) p;
            } else {
                res = storeImm(new imm(((pointerType) it.p.type).elemType.size()), 4);
                Entity idx = convertReg(it.idx.get(0), true);
                currentBlock.add(new ASM.Instruction.binary(res, idx, "mul",
                        res));
                currentBlock.add(new ASM.Instruction.binary(res, p, "add", res));
            }
        } else {
            classType Class = (classType) ((pointerType) it.p.type).elemType;
            Class = global.getClass(Class.name);
            int pos = Class.offset.get(((intEntity) it.idx.get(1)).val) * 4;
            Entity tmp = new imm(pos);
            if (pos < -2048 || pos >= 2048) tmp = storeImm((imm) tmp, 4);
            currentBlock.add(new ASM.Instruction.binary(res, p, binary.binaryOp.add, tmp, tmp instanceof imm));
        }
        if (convertedRes instanceof memory) {
            currentBlock.add(new ASM.Instruction.store(res, updateMem((memory) convertedRes)));
        }
    }

    @Override
    public void visit(IR.Instruction.icmp it) {
        reg res;
        Entity convertedRes = convertReg(it.res, false);
        if (convertedRes instanceof memory) {
            res = getTempReg(it.res.type.size());
        } else res = (reg) convertedRes;
        Entity op1 = convertReg(it.op1, true), op2 = convertReg(it.op2, true);
        if (op2 instanceof imm) {
            if (((imm) op2).val < -2048 || ((imm) op2).val >= 2048) op2 = storeImm((imm) op2, 4);
        }
        switch (it.op) {
            case eq -> {
                reg tmp = getTempReg(res.size);
                currentBlock.add(new ASM.Instruction.binary(tmp, op1, binary.binaryOp.xor,
                        op2, op2 instanceof imm));
                currentBlock.add(new comp("seqz", res, tmp));
            }
            case ne -> {
                reg tmp = getTempReg(res.size);
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
        if (convertedRes instanceof memory) {
            currentBlock.add(new ASM.Instruction.store(res, updateMem((memory) convertedRes)));
        }
    }

    @Override
    public void visit(IR.Instruction.call it) {
        for (int i = 0; i < Integer.min(8, it.args.size()); ++i) {
            Entity tmp = convertReg(it.args.get(i), true);
            if (tmp instanceof reg) currentBlock.add(new mv(program.a(i), (reg) tmp));
            else if (tmp instanceof imm) currentBlock.add(new li(program.a(i), (imm) tmp));
        }
        int callsize = 0;
        for (int i = 8; i < it.args.size(); i++) {
            Entity tmp = convertReg(it.args.get(i), true);
            if (tmp instanceof imm) tmp = storeImm((imm) tmp, 4);
            currentBlock.add(new ASM.Instruction.store((reg) tmp, new memory(program.sp, new imm((i - 8) * 4), ((reg) tmp).size)));
            callsize += 4;
        }
        currentFunction.callSize = Integer.max(currentFunction.callSize, callsize);
        currentBlock.add(new ASM.Instruction.call(it.name));
        if (it.res != null) {
            Entity convertedRes = convertReg(it.res, false);
            if (convertedRes instanceof reg) currentBlock.add(new mv((reg) convertedRes, program.a(0)));
            else {
                program.a(0).size = ((memory) convertedRes).size;
                currentBlock.add(new ASM.Instruction.store(program.a(0), updateMem((memory) convertedRes)));
            }
        }
    }

    @Override
    public void visit(IR.Instruction.phi it) {
        reg res;
        Entity convertedRes = convertReg(it.res, false);
        if (convertedRes instanceof memory) {
            res = getTempReg(it.res.type.size());
        } else res = (reg) convertedRes;
        for (int i = 0; i < it.label.size(); i++) {
            ASM.Block nowBlock;
            if (Objects.equals(it.label.get(i).name, "entry")) nowBlock = blocks.get(currentFunction.name);
            else nowBlock = blocks.get(currentFunction.name + "." + it.label.get(i).name);
            if (it.val.get(i) instanceof intEntity) nowBlock.inst.add(nowBlock.inst.size() - 1,
                    new li(res, new imm(((intEntity) it.val.get(i)).val)));
            else if (it.val.get(i) instanceof boolEntity) nowBlock.inst.add(nowBlock.inst.size() - 1,
                    new li(res, new imm(((boolEntity) it.val.get(i)).val ? 1 : 0)));
            else {
                Entity val = regs.get(currentFunction.name + "."  + it.val.get(i).toString());
                if (val == null) {
                    nowBlock.inst.add(nowBlock.inst.size() - 1, new la(res, ((globalVarEntity) it.val.get(i)).name));
                } else {
                    if (val instanceof memory) nowBlock.inst.add(nowBlock.inst.size() - 1, new ASM.Instruction.load(res, updateMem((memory) val)));
                    else nowBlock.inst.add(nowBlock.inst.size() - 1, new mv(res, (reg) val));
                }
            }
        }
        if (convertedRes instanceof memory) {
            currentBlock.add(new ASM.Instruction.store(res, updateMem((memory) convertedRes)));
        }
    }
}
