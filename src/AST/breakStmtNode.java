package AST;

import Utils.Position;
public class breakStmtNode extends flowStmtNode {
    public breakStmtNode(Position pos) {
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
