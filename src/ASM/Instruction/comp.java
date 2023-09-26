package ASM.Instruction;

import ASM.ASMVisitor;
import ASM.Entity.Entity;

public class comp extends Instruction {
    public String type;
    public Entity res, op;

    public comp(String type, Entity res, Entity op) {
        this.type = type;
        this.res = res;
        this.op = op;
    }

    @Override
    public String toString() {
        return String.format("%-8s", type) + res + ", " + op;
    }

    @Override
    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }
}
