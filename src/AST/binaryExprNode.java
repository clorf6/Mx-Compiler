package AST;

import Utils.*;
public class binaryExprNode extends ExprNode {
    public ExprNode lhs, rhs;
    public enum binaryOpType {
        Mul, Div, Mod, Add, Sub,
        Lshift, Rshift, Le, Ge, Leq, Geq,
        Eq, Neq, Bitand, Bitxor, Bitor,
        And, Or
    }

    public binaryOpType opCode;

    public binaryExprNode(Position pos, ExprNode lhs, ExprNode rhs, binaryOpType opCode) {
        super(pos);
        this.lhs = lhs;
        this.rhs = rhs;
        this.opCode = opCode;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "binaryExprNode";
    }
}
