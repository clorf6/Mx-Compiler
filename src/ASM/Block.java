package ASM;

import ASM.Instruction.Instruction;

import java.util.ArrayList;
import java.util.LinkedList;

public class Block {
    public String name;
    public LinkedList<Instruction> inst;
    public LinkedList<Instruction> phiInst;
    public LinkedList<Instruction> jumpInst;
    public boolean isFir;

    public Block(String name) {
        this.name = name;
        this.isFir = false;
        this.phiInst = new LinkedList<>();
        this.jumpInst = new LinkedList<>();
        this.inst = new LinkedList<>();
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
