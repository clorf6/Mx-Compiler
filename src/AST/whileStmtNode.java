package AST;

import Utils.Position;
public class whileStmtNode extends loopStmtNode {
    public ExprNode cond;
    public StmtNode stmt;

    public whileStmtNode(Position pos, ExprNode cond, StmtNode stmt) {
        super(pos);
        this.cond = cond;
        this.stmt = stmt;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "whileStmtNode";
    }
}
