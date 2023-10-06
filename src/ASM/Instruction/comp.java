package ASM.Instruction;

import ASM.ASMVisitor;
import ASM.Entity.Entity;
import ASM.Entity.virtualReg;

import java.util.HashSet;

public class comp extends Instruction {
    public String type;
    public Entity res, op;

    public comp(String type, Entity res, Entity op) {
        this.type = type;
        this.res = res;
        this.op = op;
    }

    @Override
    public HashSet<virtualReg> getUse() {
        HashSet<virtualReg> use = new HashSet<>();
        if (op instanceof virtualReg) use.add((virtualReg) op);
        return use;
    }

    @Override
    public HashSet<virtualReg> getDef() {
        HashSet<virtualReg> def = new HashSet<>();
        if (res instanceof virtualReg) def.add((virtualReg) res);
        return def;
    }

    @Override
    public void update() {
        if (op instanceof virtualReg) op = ((virtualReg) op).to;
        if (res instanceof virtualReg) res = ((virtualReg) res).to;
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
