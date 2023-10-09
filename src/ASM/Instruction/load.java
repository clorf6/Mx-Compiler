package ASM.Instruction;

import ASM.ASMVisitor;
import ASM.Entity.*;
import static Optimize.PeepholeOptimizer.loadMap;
import java.util.HashSet;

public class load extends Instruction {
    public String type;
    public reg rd;
    public memory ms;

    public load(reg rd, memory ms) {
        this.type = switch (rd.size) {
            case 1 -> "lb";
            case 2 -> "lh";
            default -> "lw";
        };
        this.rd = rd;
        this.ms = ms;
    }

    @Override
    public HashSet<virtualReg> getUse() {
        HashSet<virtualReg> use = new HashSet<>();
        if (ms.x instanceof virtualReg) use.add((virtualReg) ms.x);
        return use;
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
        loadMap.remove(ms.x);
    }

    @Override
    public void update() {
        if (rd instanceof virtualReg) rd = ((virtualReg) rd).to;
        if (ms.x instanceof virtualReg) ms.x = ((virtualReg) ms.x).to;
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
