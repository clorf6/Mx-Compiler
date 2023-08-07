package AST;

import Utils.Position;
public abstract class constExprNode extends ExprNode {
    public constExprNode(Position pos) {
        super(pos);
    }

    @Override
    public String toString() {
        return "constExprNode";
    }
}
