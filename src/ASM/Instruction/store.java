package ASM.Instruction;

import ASM.ASMVisitor;
import ASM.Entity.*;
import Utils.Error.assemblyError;

public class store extends Instruction {
    public String type;
    public reg val;
    public memory ms;

    public store(reg val, memory ms) {
//        if (val.size != ms.size) throw new assemblyError(null, "store size not match");
        this.type = switch (val.size) {
            case 1 -> "sb";
            case 2 -> "sh";
            default -> "sw";
        };
        this.val = val;
        this.ms = ms;
    }

    @Override
    public String toString() {
        return String.format("%-8s", type) + val + ", " + ms;
    }

    @Override
    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }
}
