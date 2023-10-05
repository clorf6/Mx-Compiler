package Backend;

import ASM.*;
import ASM.Instruction.*;
import ASM.Entity.*;
import java.util.LinkedList;
import java.util.HashMap;

public class RegAlloc implements ASMVisitor {

    public Program program;
    public Block currentBlock;
    public int currentSize;
    public HashMap<virtualReg, memory> memoryMap;
    public LinkedList<Instruction> currentInsts;

    public RegAlloc(Program program) {
        this.program = program;
        this.memoryMap = new HashMap<>();
        program.accept(this);
    }

    Entity allocate(Entity now, physicReg ret, boolean isStore) {
        if (now instanceof virtualReg) {
            memory offset;
            imm pos;
            if (memoryMap.containsKey((virtualReg) now)) {
                offset = memoryMap.get((virtualReg) now);
                pos = offset.offset;
            } else {
                pos = new imm(- currentSize - ((virtualReg) now).size);
                currentSize += ((virtualReg) now).size;
                offset = new memory(program.s(0), pos, ((virtualReg) now).size);
                memoryMap.put((virtualReg) now, offset);
            }
            if (pos.val < -2048 || pos.val >= 2048) {
                currentInsts.add(new li(program.t(3), pos));
                currentInsts.add(new binary(program.t(3), program.s(0), IR.Instruction.binary.binaryOp.add, program.t(3), false));
                offset = new memory(program.t(3), new imm(0), ((virtualReg) now).size);
            }
            ret.size = offset.size;
            if (!isStore) currentInsts.add(new load(ret, offset));
            else currentInsts.add(new store(ret, offset));
            return ret;
        } else return now;
    }

    @Override
    public void visit(Program program) {
        program.text.accept(this);
    }

    @Override
    public void visit(Section section) {
        section.func.forEach(fc -> fc.accept(this));
    }

    @Override
    public void visit(Function function) {
        currentSize = 8;
        function.block.forEach(block -> block.accept(this));
        function.size = currentSize + function.callSize;
        int size = (function.size / 16 + ((function.size % 16 != 0) ? 1 : 0)) * 16;
        Block fir = function.block.get(0);
        Block las = function.retBlock;
        program.t(0).size = 4;
        program.t(1).size = 4;
        fir.inst.add(0, new ASM.Instruction.store(program.t(1), new memory(program.s(0), new imm(-8), 4)));
        fir.inst.add(0, new ASM.Instruction.store(program.ra, new memory(program.s(0), new imm(-4), 4)));
        fir.inst.add(0, new ASM.Instruction.binary(program.sp, program.sp,
                IR.Instruction.binary.binaryOp.sub, program.t(0), false));
        fir.inst.add(0, new mv(program.s(0), program.sp));
        fir.inst.add(0, new mv(program.t(1), program.s(0)));
        fir.inst.add(0, new li(program.t(0), new imm(size)));
        Entity st = new imm(size);
        if (size < -2048 || size >= 2048) {
            st = program.t(0);
            las.inst.add(las.inst.size() - 1, new li((reg) st, new imm(size)));
        }
        las.inst.add(las.inst.size() - 1, new ASM.Instruction.binary(program.sp, program.sp, IR.Instruction.binary.binaryOp.add, st, st instanceof imm));
        las.inst.add(las.inst.size() - 1, new ASM.Instruction.load(program.s(0), new memory(program.sp, new imm(-8), 4)));
        las.inst.add(las.inst.size() - 1, new ASM.Instruction.load(program.ra, new memory(program.sp, new imm(-4), 4)));
    }

    public void visit(Block block) {
        currentBlock = block;
        currentInsts = new LinkedList<>();
        block.inst.forEach(ins -> ins.accept(this));
        currentBlock.inst = currentInsts;
    }

    @Override
    public void visit(binary inst) {
        inst.op1 = allocate(inst.op1, program.t(0), false);
        inst.op2 = allocate(inst.op2, program.t(1), false);
        currentInsts.add(inst);
        inst.res = allocate(inst.res, program.t(2), true);
    }

    @Override
    public void visit(call inst) {

        currentInsts.add(inst);
    }

    @Override
    public void visit(comp inst) {
        inst.op = allocate(inst.op, program.t(0), false);
        currentInsts.add(inst);
        inst.res = allocate(inst.res, program.t(1), true);
    }

    @Override
    public void visit(j inst) {
        currentInsts.add(inst);
    }

    @Override
    public void visit(br inst) {
        inst.op = allocate(inst.op, program.t(0), false);
        currentInsts.add(inst);
    }

    @Override
    public void visit(load inst) {
        if (inst.ms != null) {
            inst.ms.x = (reg) allocate(inst.ms.x, program.t(0), false);
        }
        currentInsts.add(inst);
        inst.rd = (reg) allocate(inst.rd, program.t(1), true);
    }

    @Override
    public void visit(store inst) {
        if (inst.ms != null) {
            inst.ms.x = (reg) allocate(inst.ms.x, program.t(0), false);
        }
        inst.val = (reg) allocate(inst.val, program.t(1), false);
        currentInsts.add(inst);
    }

    @Override
    public void visit(mv inst) {
        inst.rs = (reg) allocate(inst.rs, program.t(0), false);
        currentInsts.add(inst);
        inst.rd = (reg) allocate(inst.rd, program.t(1), true);
    }

    @Override
    public void visit(ret inst) {
        currentInsts.add(inst);
    }

    @Override
    public void visit(li inst) {
        currentInsts.add(inst);
        inst.rd = (reg) allocate(inst.rd, program.t(0), true);
    }

    @Override
    public void visit(la inst) {
        currentInsts.add(inst);
        inst.rd = (reg) allocate(inst.rd, program.t(0), true);
    }
}
