package AST;

import Utils.position;

abstract public class ASTNode {
    public position pos;

    public ASTNode(position pos) {
        this.pos = pos;
    }

    abstract public void accept(ASTVisitor visitor);

    abstract public String toString();
}