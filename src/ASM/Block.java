package ASM;

import ASM.Instruction.Instruction;

import java.util.ArrayList;

public class Block {
    public String name;
    public ArrayList<Instruction> inst;
    public boolean isFir;

    public Block(String name) {
        this.name = name;
        this.isFir = false;
        this.inst = new ArrayList<>();
    }

    public void add(Instruction ins) {
        inst.add(ins);
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
