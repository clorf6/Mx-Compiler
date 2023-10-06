package ASM;

import ASM.Instruction.Instruction;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import ASM.Entity.virtualReg;

public class Block {
    public String name;
    public int beg;
    public LinkedList<Block> pre;
    public LinkedList<Block> suc;
    public HashSet<virtualReg> use, def;
    public HashSet<virtualReg> in, out;
    public LinkedList<Instruction> inst;
    public LinkedList<Instruction> phiInst;
    public LinkedList<Instruction> jumpInst;

    public Block(String name) {
        this.name = name;
        this.phiInst = new LinkedList<>();
        this.jumpInst = new LinkedList<>();
        this.inst = new LinkedList<>();
        this.pre = new LinkedList<>();
        this.suc = new LinkedList<>();
        this.in = new HashSet<>();
        this.out = new HashSet<>();
        this.use = new HashSet<>();
        this.def = new HashSet<>();
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
