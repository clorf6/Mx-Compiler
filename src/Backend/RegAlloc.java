package Backend;

import ASM.*;
import ASM.Instruction.*;
import ASM.Entity.*;
import java.util.ArrayList;
import java.util.HashMap;

public class RegAlloc implements ASMVisitor {

    public Program program;
    public Block currentBlock;
    public int currentSize;
    public HashMap<virtualReg, memory> memoryMap;
    public ArrayList<Instruction> currentInsts;

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
                pos = (imm) offset.offset;
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
        section.block.forEach(block -> block.accept(this));
    }

    public void visit(Block block) {
        if (block.isFir) currentSize = 8;
        currentBlock = block;
        currentInsts = new ArrayList<>();
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
