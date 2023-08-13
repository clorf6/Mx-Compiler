package AST;

import IR.Entity.Entity;
import Utils.*;
import AST.Type.Type;

public abstract class ExprNode extends ASTNode {

    public Type type;

    public Entity entity;
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
