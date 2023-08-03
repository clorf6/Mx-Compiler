package AST;

import Utils.position;
public abstract class constExprNode extends ExprNode {
    public constExprNode(position pos) {
        super(pos);
    }

    @Override
    public String toString() {
        return "constExprNode";
    }
}
