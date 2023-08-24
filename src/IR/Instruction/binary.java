package IR.Instruction;


import AST.binaryExprNode;
import IR.IRVisitor;
import IR.Entity.Entity;
import Utils.Error.internalError;
import Utils.Position;

public class binary extends Instruction {
    public enum binaryOp {
        add, sub, mul, sdiv, srem,
        shl, ashr, and, or, xor
    }

    public Entity res, op1, op2;
    public binaryOp op;

    public binary(Entity res, Entity op1, binaryExprNode.binaryOpType op, Entity op2, Position pos) {
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
            default -> throw new internalError(pos, "Wrong binary type");
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
