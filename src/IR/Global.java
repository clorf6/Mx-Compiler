package IR;

import IR.Instruction.Instruction;

import java.util.ArrayList;

public class Global {
    ArrayList<Instruction> insts;

    public Global() {
        insts = new ArrayList<>();
    }

    public void add(Instruction inst) {
        insts.add(inst);
    }

    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (Instruction ins : insts) {
            ret.append(ins).append("\n");
        }
        ret.append("\n");
        return ret.toString();
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    public static class Class {
    }
}
