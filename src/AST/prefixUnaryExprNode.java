package AST;

import Utils.*;
public class prefixUnaryExprNode extends ExprNode {
    public ExprNode expr;
    public enum prefixOpType {
        Inc, Dec, Not, Inv, Sub
    }

    public prefixOpType opCode;

    public prefixUnaryExprNode(Position pos, ExprNode expr, prefixOpType opCode) {
        super(pos);
        this.expr = expr;
        this.opCode = opCode;
        this.isAssign = true;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "prefixUnaryExprNode";
    }
}
