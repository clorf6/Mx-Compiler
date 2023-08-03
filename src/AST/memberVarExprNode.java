package AST;

import Utils.*;
public class memberVarExprNode extends ExprNode {
    public ExprNode expr;
    String name;
    public memberVarExprNode(position pos, ExprNode expr, String name) {
        super(pos);
        this.expr = expr;
        this.name = name;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "memberVarExprNode";
    }
}
