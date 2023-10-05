package ASM;

import ASM.Instruction.Instruction;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import ASM.Entity.virtualReg;

public class Block {
    public String name;
    public int pos, beg, end;
    public LinkedHashSet<Block> pre;
    public LinkedHashSet<Block> suc;
    public LinkedHashSet<virtualReg> use, def;
    public LinkedHashSet<virtualReg> in, out;
    public LinkedList<Instruction> inst;
    public LinkedList<Instruction> phiInst;
    public LinkedList<Instruction> jumpInst;

    public Block(String name) {
        this.name = name;
        this.pos = -1;
        this.phiInst = new LinkedList<>();
        this.jumpInst = new LinkedList<>();
        this.inst = new LinkedList<>();
        this.pre = new LinkedHashSet<>();
        this.suc = new LinkedHashSet<>();
        this.in = new LinkedHashSet<>();
        this.out = new LinkedHashSet<>();
        this.use = new LinkedHashSet<>();
        this.def = new LinkedHashSet<>();
    }

    public void add(Instruction ins) {
        inst.add(ins);
    }

    public void merge() {
        inst.addAll(phiInst);
        inst.addAll(jumpInst);
    }

    public String toString() {
        StringBuilder ret = new StringBuilder(name + ":\n");
        for (Instruction ins : inst) {
            ret.append("\t").append(ins.toString()).append("\n");
        }
        return ret.toString();
    }
    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }
}
