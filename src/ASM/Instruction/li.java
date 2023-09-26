package ASM.Instruction;

import ASM.ASMVisitor;
import ASM.Entity.*;

public class li extends Instruction {
    public reg rd;
    public imm val;

    public li(reg rd, imm val) {
        this.rd = rd;
        this.val = val;
    }

    @Override
    public String toString() {
        return String.format("%-8s", "li") + rd + ", " + val;
    }

    @Override
    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }
}
