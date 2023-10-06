package ASM.Instruction;

import ASM.ASMVisitor;
import ASM.Entity.virtualReg;

import java.util.HashSet;

public class j extends Instruction {
    public String label;

    public j(String label) {
        this.label = label;
    }

    @Override
    public HashSet<virtualReg> getUse() {
        return new HashSet<>();
    }

    @Override
    public HashSet<virtualReg> getDef() {
        return new HashSet<>();
    }

    @Override
    public void update() {

    }

    @Override
    public String toString() {
        return String.format("%-8s", "j") + label;
    }

    @Override
    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }
}
