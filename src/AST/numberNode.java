package AST;

import Utils.*;
public class numberNode extends constExprNode {
    public long val;

    public numberNode(Position pos, long val) {
        super(pos);
        this.val = val;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "numberNode";
    }

}
