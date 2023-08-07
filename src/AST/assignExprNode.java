package AST;

import Utils.*;
public class assignExprNode extends ExprNode {
    public ExprNode lhs, rhs;

    public assignExprNode(Position pos, ExprNode lhs, ExprNode rhs) {
        super(pos);
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "assignExprNode";
    }
}
