package AST;

import Utils.*;
public class nullNode extends constExprNode {
    public nullNode(Position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "nullNode";
    }
}
