package AST;

import Utils.*;
public class memberVarExprNode extends ExprNode {
    public ExprNode expr;
    public String name;
    public memberVarExprNode(Position pos, ExprNode expr, String name) {
        super(pos);
        this.expr = expr;
        this.name = name;
        this.isAssign = true;
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
