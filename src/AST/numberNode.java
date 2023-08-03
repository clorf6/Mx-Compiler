package AST;

import Utils.*;
public class numberNode extends constExprNode {
    public int val;

    public numberNode(position pos, int val) {
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
