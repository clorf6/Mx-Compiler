package ASM.Instruction;

import ASM.ASMVisitor;
import ASM.Entity.reg;

public class mv extends Instruction {
    public reg rd, rs;
    public mv(reg rd, reg rs) {
        this.rd = rd;
        this.rs = rs;
    }

    @Override
    public String toString() {
        return String.format("%-8s", "mv") + rd + ", " + rs;
    }

    @Override
    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }
}
