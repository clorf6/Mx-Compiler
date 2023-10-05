package ASM.Instruction;

import ASM.ASMVisitor;
import ASM.Entity.*;
import Utils.Error.assemblyError;

import java.util.HashSet;

public class store extends Instruction {
    public String type;
    public reg val;
    public memory ms;

    public store(reg val, memory ms) {
        this.type = switch (val.size) {
            case 1 -> "sb";
            case 2 -> "sh";
            default -> "sw";
        };
        this.val = val;
        this.ms = ms;
    }

    @Override
    public HashSet<virtualReg> getUse() {
        HashSet<virtualReg> use = new HashSet<>();
        if (ms.x instanceof virtualReg) use.add((virtualReg) ms.x);
        if (val instanceof virtualReg) use.add((virtualReg) val);
        return use;
    }

    @Override
    public HashSet<virtualReg> getDef() {
        return new HashSet<>();
    }

    @Override
    public String toString() {
        return String.format("%-8s", type) + val + ", " + ms;
    }

    @Override
    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }
}
