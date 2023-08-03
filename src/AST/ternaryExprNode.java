package AST;

import Utils.*;
public class ternaryExprNode extends ExprNode {
    public ExprNode cond, thenExpr, elseExpr;

    public ternaryExprNode(position pos, ExprNode cond,
                           ExprNode thenExpr, ExprNode elseExpr) {
        super(pos);
        this.cond = cond;
        this.thenExpr = thenExpr;
        this.elseExpr = elseExpr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "ternaryExprNode";
    }
}
