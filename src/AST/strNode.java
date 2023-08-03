package AST;

import Utils.*;
public class strNode extends constExprNode {
    public String val;

    public strNode(position pos, String val) {
        super(pos);
        this.val = val;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "strNode";
    }

}
