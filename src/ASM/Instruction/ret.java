package ASM.Instruction;

import ASM.ASMVisitor;

public class ret extends Instruction {
    @Override
    public String toString() {
        return String.format("%-8s", "ret");
    }

    @Override
    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }
}
