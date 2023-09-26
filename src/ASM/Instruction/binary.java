package ASM.Instruction;

import ASM.ASMVisitor;
import ASM.Entity.Entity;
import IR.Instruction.*;
import Utils.Error.assemblyError;
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
            default -> throw new assemblyError(null, "Wrong binary type");
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
    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }
}
