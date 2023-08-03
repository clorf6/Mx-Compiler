package AST;

import Utils.*;

public abstract class ExprNode extends ASTNode {
    public ExprNode(position pos) {
        super(pos);
    }

    @Override
    public String toString() {
        return "ExprNode";
    }
}
