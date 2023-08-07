package AST;

import Utils.Position;
public class returnStmtNode extends flowStmtNode {
    public ExprNode ret;

    public returnStmtNode(Position pos, ExprNode ret) {
        super(pos);
        this.ret = ret;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "returnStmtNode";
    }
}
