package ASM.Instruction;

import ASM.Entity.Entity;

public class br extends Instruction {
    public String type, label;
    public Entity op1, op2;

    public br(String type, Entity op1, Entity op2, String label) {
        this.type = type;
        this.op1 = op1;
        this.op2 = op2;
        this.label = label;
    }

    public br(String type, Entity op, String label) {
        this.type = type;
        this.op1 = op;
        this.op2 = null;
        this.label = label;
    }

    @Override
    public String toString() {
        if (op2 == null) {
            return String.format("%-8s", type) + op1 + ", " + label;
        } else {
            return String.format("%-8s", type) + op1 + ", " + op2 + ", " + label;
        }
    }
}
