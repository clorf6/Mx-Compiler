package AST;

import Utils.*;
import AST.Type.Type;

public abstract class ExprNode extends ASTNode {

    public Type type;
    public boolean isAssign;

    public ExprNode(Position pos) {
        super(pos);
        this.isAssign = false;
    }

    @Override
    public String toString() {
        return "ExprNode";
    }
}
