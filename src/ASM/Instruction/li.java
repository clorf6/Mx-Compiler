package ASM.Instruction;

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
}
