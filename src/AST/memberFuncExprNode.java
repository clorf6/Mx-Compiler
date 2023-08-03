package AST;

import Utils.*;
public class memberFuncExprNode extends ExprNode {
    public ExprNode expr;
    public funcExprNode func;
    public memberFuncExprNode(position pos, ExprNode expr, funcExprNode func) {
        super(pos);
        this.expr = expr;
        this.func = func;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "memberFuncExprNode";
    }
}
