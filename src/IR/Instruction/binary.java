package IR.Instruction;


import AST.binaryExprNode;
import IR.IRVisitor;
import IR.Entity.Entity;
import Utils.Error.internalError;

public class binary extends Instruction {
    public enum binaryOp {
        add, sub, mul, udiv, sdiv,
        srem, shl, ashr, and, or, xor
    }

    Entity res, op1, op2;
    binaryOp op;

    public binary(Entity res, Entity op1, binaryExprNode.binaryOpType op, Entity op2) {
        this.res = res;
        this.op1 = op1;
        this.op2 = op2;
        this.op = switch (op) {
            case Add -> binaryOp.add;
            case Sub -> binaryOp.sub;
            case Mul -> binaryOp.mul;
            case Div -> binaryOp.sdiv;
            case Mod -> binaryOp.srem;
            case Lshift -> binaryOp.shl;
            case Rshift ->  binaryOp.ashr;
            case Bitand, And -> binaryOp.and;
            case Bitor, Or -> binaryOp.or;
            case Bitxor -> binaryOp.xor;
            default -> throw new internalError(null, "Wrong binary type");
        };
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return res.getText() + " = " + op.name()
                + " " + res.type.toString() + " " + op1.getText()
                + ", " + op2.getText();
    }
}
