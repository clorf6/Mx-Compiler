package AST;

import Utils.position;
public class continueStmtNode extends flowStmtNode {
    public continueStmtNode(position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "continueStmtNode";
    }
}
