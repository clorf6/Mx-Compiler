package AST;

import Utils.*;
public class varExprNode extends ExprNode {
    public String name;
    public varExprNode(Position pos, String name) {
        super(pos);
        this.name = name;
        this.isAssign = true;
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
