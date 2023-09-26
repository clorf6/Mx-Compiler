package ASM.Instruction;

import ASM.ASMVisitor;
import ASM.Entity.*;
import Utils.Error.assemblyError;
import org.antlr.v4.runtime.atn.SemanticContext;

public class load extends Instruction {
    public String type;
    public reg rd;
    public memory ms;

    public load(reg rd, memory ms) {
//        if (rd.size != ms.size) throw new assemblyError(null, "load size not match");
        this.type = switch (rd.size) {
            case 1 -> "lb";
            case 2 -> "lh";
            default -> "lw";
        };
        this.rd = rd;
        this.ms = ms;
    }

    @Override
    public String toString() {
        return String.format("%-8s", type) + rd + ", " + ms;
    }

    @Override
    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }
}
