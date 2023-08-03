package AST;

import Utils.position;
public class breakStmtNode extends flowStmtNode {
    public breakStmtNode(position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "breakStmtNode";
    }
}
