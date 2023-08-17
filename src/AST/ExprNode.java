package AST;

import IR.Entity.Entity;
import IR.Entity.varEntity;
import Utils.*;
import AST.Type.Type;

public abstract class ExprNode extends ASTNode {

    public Type type;

    public Entity entity;
    public varEntity addr;
    public boolean isAssign, getEntity;

    public ExprNode(Position pos) {
        super(pos);
        this.isAssign = false;
        this.getEntity = true;
    }

    @Override
    public String toString() {
        return "ExprNode";
    }
}
