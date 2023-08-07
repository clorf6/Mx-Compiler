package AST;

import Utils.*;

public class varNode extends ASTNode {
    public String name;
    public ExprNode init;

    public varNode(Position pos, String name, ExprNode init) {
        super(pos);
        this.name = name;
        this.init = init;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "varNode";
    }

}
