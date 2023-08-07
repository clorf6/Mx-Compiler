package AST;

import Utils.*;
public class boolNode extends constExprNode {
    public boolean val;

    public boolNode(Position pos, boolean val) {
        super(pos);
        this.val = val;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "boolNode";
    }

}
