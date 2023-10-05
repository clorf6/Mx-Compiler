package ASM.Instruction;

import ASM.ASMVisitor;
import ASM.Entity.virtualReg;

import java.util.HashSet;

public class ret extends Instruction {
    @Override
    public String toString() {
        return String.format("%-8s", "ret");
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
    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }
}
