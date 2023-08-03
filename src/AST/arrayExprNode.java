package AST;

import Utils.*;
public class arrayExprNode extends ExprNode {
    public ExprNode name;
    public ExprNode index;

    public arrayExprNode(position pos, ExprNode name, ExprNode index) {
        super(pos);
        this.name = name;
        this.index = index;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "arrayExprNode";
    }
}
