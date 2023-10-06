package ASM.Instruction;

import ASM.ASMVisitor;
import ASM.Entity.*;

import java.util.HashSet;

public class la extends Instruction {
    public reg rd;
    public String label;

    public la(reg rd, String label) {
        this.rd = rd;
        this.label = label;
    }

    @Override
    public HashSet<virtualReg> getUse() {
        return new HashSet<>();
    }

    @Override
    public HashSet<virtualReg> getDef() {
        HashSet<virtualReg> def = new HashSet<>();
        if (rd instanceof virtualReg) def.add((virtualReg) rd);
        return def;
    }

    @Override
    public void update() {
        if (rd instanceof virtualReg) rd = ((virtualReg) rd).to;
    }
    @Override
    public String toString() {
        return String.format("%-8s", "la") + rd + ", " + label;
    }

    @Override
    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }
}
