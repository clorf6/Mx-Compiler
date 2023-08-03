package AST;

import Utils.position;
public class returnStmtNode extends flowStmtNode {
    public ExprNode ret;

    public returnStmtNode(position pos, ExprNode ret) {
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
