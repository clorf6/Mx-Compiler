package ASM.Instruction;

import ASM.ASMVisitor;
import ASM.Entity.Entity;
import ASM.Entity.virtualReg;

import java.util.HashSet;

public class br extends Instruction {
    public String type, label;
    public Entity op;
    public br(String type, Entity op, String label) {
        this.type = type;
        this.op = op;
        this.label = label;
    }

    @Override
    public String toString() {
        return String.format("%-8s", type) + op + ", " + label;
    }

    @Override
    public HashSet<virtualReg> getUse() {
        HashSet<virtualReg> use = new HashSet<>();
        if (op instanceof virtualReg) use.add((virtualReg) op);
        return use;
    }

    public HashSet<virtualReg> getDef() {
        return new HashSet<>();
    }
    @Override
    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }
}
