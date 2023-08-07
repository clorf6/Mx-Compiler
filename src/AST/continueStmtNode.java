package AST;

import Utils.Position;
public class continueStmtNode extends flowStmtNode {
    public continueStmtNode(Position pos) {
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
