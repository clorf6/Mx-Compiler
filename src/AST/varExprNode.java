package AST;

import Utils.*;
public class varExprNode extends ExprNode {
    String name;
    public varExprNode(position pos, String name) {
        super(pos);
        this.name = name;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "varExprNode";
    }
}
