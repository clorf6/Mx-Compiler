package Optimize;

import java.util.*;
import java.util.stream.Collectors;

import ASM.*;
import ASM.Entity.*;
import ASM.Instruction.*;
import org.antlr.runtime.tree.Tree;

public class LinearScan implements ASMVisitor {

    public Program program;
    public Block currentBlock;
    public ArrayList<virtualReg> all;
    public ArrayList<Integer> callPos;
    public int callNow;
    public TreeSet<virtualReg> active;
    public physicReg[] allocaReg;
    public physicReg[] tempReg;
    public int currentSize;
    public int currentPos;
    public Comparator<virtualReg> comp = new virtualReg.regComparator();
    public HashMap<virtualReg, memory> memoryMap;
    public LinkedList<Instruction> currentInsts;
    public LinearScan(Program program) {
        this.program = program;
        this.all = new ArrayList<>();
        this.active = new TreeSet<>( new virtualReg.regComparator2());
        this.callPos = new ArrayList<>();
        this.memoryMap = new HashMap<>();
        this.allocaReg = new physicReg[23];
        this.tempReg = new physicReg[2];
        for (int i = 0; i <= 6; i++) allocaReg[i] = program.t(i);
        for (int i = 7; i <= 15; i++) allocaReg[i] = program.s(i - 6);
        for (int i = 16; i <= 20; i++) allocaReg[i] = program.a(i - 13);
        allocaReg[21] = program.gp;
        allocaReg[22] = program.tp;
        for (int i = 0; i <= 1; i++) tempReg[i] = program.s(i + 10);
        visit(program);
    }

    public void collectInterval(Function func) {
        new LivenessAnalyzer(func);
        currentPos = callNow = 0;
        all.clear();
        active.clear();
        callPos.clear();
        for (int i = 0; i < 23; i++) allocaReg[i].free = true;
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
        for (var bl : function.RPO) {
            for (var ins : bl.inst) regAlloc(ins.pos);
        }
        for (var bl : function.RPO) bl.accept(this);
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
        for (var i = active.iterator(); i.hasNext();) {
            var reg = i.next();
            if (reg.end < tim) {
                if (reg.to instanceof physicReg) ((physicReg) reg.to).free = true;
                i.remove();
            }
        }
        while (currentPos < all.size() && all.get(currentPos).beg == tim) {
            var now = all.get(currentPos);
            currentPos++;
            if (callNow < callPos.size() && now.end > callPos.get(callNow)) continue;
            boolean spill = true;
            for (int i = 0; i <= 22; i++) {
                if (allocaReg[i].free) {
                    allocaReg[i].free = false;
                    active.add(now);
                    allocaReg[i].size = now.size;
                    now.to = allocaReg[i];
                    spill = false;
                    break;
                }
            }
            if (spill) Spill(now);
        }
    }

    void Spill(virtualReg now) {
        virtualReg las = active.last();
        if (las.end > now.end) {
            physicReg p = (physicReg) las.to;
            p.size = now.size;
            las.to = las;
            now.to = p;
            active.remove(las);
            active.add(now);
        }
    }

    @Override
    public void visit(Block block) {
        currentBlock = block;
        currentInsts = new LinkedList<>();
        block.inst.forEach(ins -> {
            ins.update();
            ins.accept(this);
        });
        currentBlock.inst = currentInsts;
    }

    @Override
    public void visit(binary inst) {
        inst.op1 = Allocate(inst.op1, tempReg[0], false);
        inst.op2 = Allocate(inst.op2, tempReg[1], false);
        currentInsts.add(inst);
        inst.res = Allocate(inst.res, tempReg[0], true);
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