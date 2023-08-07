package AST;

import Utils.*;
public class suffixUnaryExprNode extends ExprNode {
    public ExprNode expr;
    public enum suffixOpType {
        Inc, Dec
    }

    public suffixOpType opCode;

    public suffixUnaryExprNode(Position pos, ExprNode expr, suffixOpType opCode) {
        super(pos);
        this.expr = expr;
        this.opCode = opCode;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "suffixUnaryExprNode";
    }
}
