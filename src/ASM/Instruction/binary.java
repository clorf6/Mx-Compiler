package ASM.Instruction;

import ASM.ASMVisitor;
import ASM.Entity.*;

import java.util.HashSet;

import static Optimize.PeepholeOptimizer.loadMap;

public class binary extends Instruction {

    public Entity res, op1, op2;
    public String op;
    public boolean isImm;
    public binary(Entity res, Entity op1, IR.Instruction.binary.binaryOp op, Entity op2, boolean isImm) {
        this.res = res;
        this.op1 = op1;
        this.op2 = op2;
        this.isImm = isImm;
        this.op = switch (op) {
            case add -> "add";
            case sub -> "sub";
            case mul -> "mul";
            case sdiv -> "div";
            case srem -> "rem";
            case shl -> "sll";
            case ashr -> "sra";
            case and -> "and";
            case or -> "or";
            case xor -> "xor";
        };
    }

    public binary(Entity res, Entity op1, String op, Entity op2) {
        this.res = res;
        this.op1 = op1;
        this.op = op;
        this.op2 = op2;
    }

    @Override
    public String toString() {
        return String.format("%-8s", op + (isImm ? "i" : "")) +
                res + ", " + op1 + ", " + op2;
    }

    @Override
    public HashSet<virtualReg> getUse() {
        HashSet<virtualReg> use = new HashSet<>();
        if (op1 instanceof virtualReg) use.add((virtualReg) op1);
        if (op2 instanceof virtualReg) use.add((virtualReg) op2);
        return use;
    }

    @Override
    public HashSet<virtualReg> getDef() {
        HashSet<virtualReg> def = new HashSet<>();
        if (res instanceof virtualReg) def.add((virtualReg) res);
        return def;
    }

    @Override
    public void updateUsed() {
        if (res instanceof reg) loadMap.remove(res);
        if (op1 instanceof reg) loadMap.remove(op1);
        if (op2 instanceof reg) loadMap.remove(op2);
    }

    @Override
    public void update() {
        if (op1 instanceof virtualReg) op1 = ((virtualReg) op1).to;
        if (op2 instanceof virtualReg) op2 = ((virtualReg) op2).to;
        if (res instanceof virtualReg) res = ((virtualReg) res).to;
    }

    @Override
    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }
}
