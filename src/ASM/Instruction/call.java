package ASM.Instruction;

import ASM.ASMVisitor;
import ASM.Entity.virtualReg;
import ASM.Program;
import java.util.HashSet;

public class call extends Instruction {
    String label;
    Program program;

    public call(String label, Program program) {
        this.label = label;
        this.program = program;
    }

    @Override
    public String toString() {
        return String.format("%-8s", "call") + label;
    }

    @Override
    public HashSet<virtualReg> getUse() {
        return new HashSet<>();
    }

    @Override
    public HashSet<virtualReg> getDef() {
        return new HashSet<>();
    }

    @Override
    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }
}
