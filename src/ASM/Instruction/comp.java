package ASM.Instruction;

import ASM.Entity.Entity;

public class comp extends Instruction {
    public String type;
    public Entity op1, op2;

    public comp(String type, Entity op1, Entity op2) {
        this.type = type;
        this.op1 = op1;
        this.op2 = op2;
    }

    @Override
    public String toString() {
        return String.format("%-8s", type) + op1 + ", " + op2;
    }
}
