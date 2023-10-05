package ASM.Instruction;

import ASM.ASMVisitor;
import ASM.Entity.virtualReg;
import ASM.Entity.reg;

import java.util.HashSet;

public class mv extends Instruction {
    public reg rd, rs;
    public mv(reg rd, reg rs) {
        this.rd = rd;
        this.rs = rs;
    }

    @Override
    public HashSet<virtualReg> getUse() {
        HashSet<virtualReg> use = new HashSet<>();
        if (rs instanceof virtualReg) use.add((virtualReg) rs);
        return use;
    }

    @Override
    public HashSet<virtualReg> getDef() {
        HashSet<virtualReg> def = new HashSet<>();
        if (rd instanceof virtualReg) def.add((virtualReg) rd);
        return def;
    }

    @Override
    public String toString() {
        return String.format("%-8s", "mv") + rd + ", " + rs;
    }

    @Override
    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }
}
