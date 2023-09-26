package ASM.Instruction;

import ASM.ASMVisitor;
import ASM.Entity.*;

public class la extends Instruction {
    public reg rd;
    public String label;

    public la(reg rd, String label) {
        this.rd = rd;
        this.label = label;
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
