package ASM.Instruction;

import ASM.ASMVisitor;
import ASM.Entity.*;
import static Optimize.PeepholeOptimizer.loadMap;
import java.util.HashSet;

public class li extends Instruction {
    public reg rd;
    public imm val;

    public li(reg rd, imm val) {
        this.rd = rd;
        this.val = val;
    }

    @Override
    public HashSet<virtualReg> getUse() {
        return new HashSet<>();
    }

    @Override
    public HashSet<virtualReg> getDef() {
        HashSet<virtualReg> def = new HashSet<>();
        if (rd instanceof virtualReg) def.add((virtualReg) rd);
        return def;
    }

    @Override
    public void updateUsed() {
        loadMap.remove(rd);
    }

    @Override
    public void update() {
        if (rd instanceof virtualReg) rd = ((virtualReg) rd).to;
    }

    @Override
    public String toString() {
        return String.format("%-8s", "li") + rd + ", " + val;
    }

    @Override
    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }
}
