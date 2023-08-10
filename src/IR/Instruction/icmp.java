package IR.Instruction;

import IR.IRVisitor;
import IR.Entity.Entity;
import IR.Entity.boolEntity;
import AST.binaryExprNode;
import Utils.Error.internalError;

public class icmp extends Instruction {
    public enum compOp {
        eq, ne, sgt, sge, slt, sle
    }

    boolEntity res;
    Entity op1, op2;
    compOp op;

    public icmp(boolEntity res, binaryExprNode.binaryOpType op,
                Entity op1, Entity op2) {
        this.res = res;
        this.op1 = op1;
        this.op2 = op2;
        this.op = switch (op) {
            case Eq -> compOp.eq;
            case Neq -> compOp.ne;
            case Le -> compOp.slt;
            case Leq -> compOp.sle;
            case Ge -> compOp.sgt;
            case Geq -> compOp.sge;
            default -> throw new internalError(null, "Icmp instruction type wrong");
        };
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return res.getText() + " = icmp " + op.name() +
                " " + op1.type.toString() + " " + op1.getText() +
                ", " + op2.getText();
    }
}
