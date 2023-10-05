package Optimize;

import java.util.*;
import java.util.stream.Collectors;

import ASM.*;
import ASM.Entity.*;
import ASM.Instruction.*;

public class LinearScan implements ASMVisitor {

    public Program program;
    public Block currentBlock;
    public ArrayList<virtualReg> all;
    public ArrayList<Integer> callPos;
    public int callNow;
    public LinkedList<virtualReg> active;
    public physicReg[] allocaReg;
    public physicReg[] tempReg;
    public int currentSize;
    public int currentPos;
    public boolean[] freeReg;
    public Comparator<virtualReg> comp = new virtualReg.regComparator();
    public LinkedHashMap<virtualReg, memory> memoryMap;
    public LinkedHashMap<virtualReg, Integer> regMap;
    public LinkedList<Instruction> currentInsts;
    public LinearScan(Program program) {
        this.program = program;
        this.all = new ArrayList<>();
        this.active = new LinkedList<>();
        this.callPos = new ArrayList<>();
        this.memoryMap = new LinkedHashMap<>();
        this.regMap = new LinkedHashMap<>();
        this.freeReg = new boolean[15];
        this.allocaReg = new physicReg[15];
        this.tempReg = new physicReg[3];
        for (int i = 0; i <= 6; i++) allocaReg[i] = program.t(i);
        for (int i = 7; i <= 14; i++) allocaReg[i] = program.s(i - 6);
        for (int i = 0; i <= 2; i++) tempReg[i] = program.s(i + 9);
        visit(program);
    }

    public void collectInterval(Function func) {
        new LivenessAnalyzer(func);
        currentPos = callNow = 0;
        all.clear();
        active.clear();
        callPos.clear();
        for (int i = 0; i < 15; i++) freeReg[i] = true;
        for (var block : func.block) {
            for (var inst : block.inst) {
                if (inst instanceof call) callPos.add(inst.pos);
                all.addAll(inst.getUse());
                all.addAll(inst.getDef());
            }
        }
        all.sort(comp);
        all = (ArrayList<virtualReg>) all.stream().distinct().collect(Collectors.toList());
        //for (var reg : all) System.out.println(reg + " " + reg.beg + " " + reg.end);
    }

    Entity Allocate(Entity now, physicReg tmp, boolean isStore) {
        if (now instanceof virtualReg) {
            if (regMap.containsKey((virtualReg) now)) {
                return allocaReg[regMap.get((virtualReg) now)];
            }
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
                currentInsts.add(new li(tmp, pos));
                currentInsts.add(new binary(tmp, program.s(0), IR.Instruction.binary.binaryOp.add, tmp, false));
                offset = new memory(tmp, new imm(0), ((virtualReg) now).size);
            }
            tmp.size = offset.size;
            if (!isStore) currentInsts.add(new load(tmp, offset));
            else currentInsts.add(new store(tmp, offset));
            return tmp;
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
        collectInterval(function);
        for (int i = function.RPO.size() - 1; i >= 0; i--) function.RPO.get(i).accept(this);
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

    public void regAlloc(int tim) {
        if (callNow < callPos.size() && tim == callPos.get(callNow)) callNow++;
        //System.out.println(tim);
        //for (int i = 0; i < 15; i++) System.out.println(i + " " + freeReg[i]);
        for (int i = 0; i < active.size(); i++) {
            var reg = active.get(i);
            if (reg.end < tim) {
                Integer id = regMap.get(reg);
                if (id != null) {
                    freeReg[id] = true;
                    regMap.remove(reg);
                }
                active.remove(i);
                i--;
            }
        }
        while (currentPos < all.size() && all.get(currentPos).beg == tim) {
            active.addLast(all.get(currentPos));
            currentPos++;
            if (callNow < callPos.size() && active.getLast().end > callPos.get(callNow)) continue;
            for (int i = 0; i <= 14; i++) {
                if (freeReg[i]) {
                    freeReg[i] = false;
                    allocaReg[i].size = active.getLast().size;
                    regMap.put(active.getLast(), i);
                    break;
                }
            }
        }
    }

    @Override
    public void visit(Block block) {
        currentBlock = block;
        currentInsts = new LinkedList<>();
        for (var ins : block.inst) {
            regAlloc(ins.pos);
            ins.accept(this);
        }
        currentBlock.inst = currentInsts;
    }

    @Override
    public void visit(binary inst) {
        inst.op1 = Allocate(inst.op1, tempReg[0], false);
        inst.op2 = Allocate(inst.op2, tempReg[1], false);
        currentInsts.add(inst);
        inst.res = Allocate(inst.res, tempReg[2], true);
    }

    @Override
    public void visit(call inst) {
        currentInsts.add(inst);
    }

    @Override
    public void visit(comp inst) {
        inst.op = Allocate(inst.op, tempReg[0], false);
        currentInsts.add(inst);
        inst.res = Allocate(inst.res, tempReg[1], true);
    }

    @Override
    public void visit(j inst) {
        currentInsts.add(inst);
    }

    @Override
    public void visit(br inst) {
        inst.op = Allocate(inst.op, tempReg[0], false);
        currentInsts.add(inst);
    }

    @Override
    public void visit(load inst) {
        if (inst.ms != null) {
            inst.ms.x = (reg) Allocate(inst.ms.x, tempReg[0], false);
        }
        currentInsts.add(inst);
        inst.rd = (reg) Allocate(inst.rd, tempReg[1], true);
    }

    @Override
    public void visit(store inst) {
        if (inst.ms != null) {
            inst.ms.x = (reg) Allocate(inst.ms.x, tempReg[0], false);
        }
        inst.val = (reg) Allocate(inst.val, tempReg[1], false);
        currentInsts.add(inst);
    }

    @Override
    public void visit(mv inst) {
        inst.rs = (reg) Allocate(inst.rs, tempReg[0], false);
        currentInsts.add(inst);
        inst.rd = (reg) Allocate(inst.rd, tempReg[1], true);
    }

    @Override
    public void visit(ret inst) {
        currentInsts.add(inst);
    }

    @Override
    public void visit(li inst) {
        currentInsts.add(inst);
        inst.rd = (reg) Allocate(inst.rd, tempReg[0], true);
    }

    @Override
    public void visit(la inst) {
        currentInsts.add(inst);
        inst.rd = (reg) Allocate(inst.rd, tempReg[0], true);
    }
}
