package ASM.Instruction;

import ASM.ASMVisitor;
import ASM.Entity.Entity;

public class br extends Instruction {
        public String type, label;
        public Entity op;
    public br(String type, Entity op, String label) {
        this.type = type;
        this.op = op;
        this.label = label;
    }

    @Override
    public String toString() {
        return String.format("%-8s", type) + op + ", " + label;
    }

    @Override
    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }
}
