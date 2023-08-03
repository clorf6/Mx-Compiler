package AST;

import Utils.position;

public class branchStmtNode extends StmtNode {
    public ExprNode cond;
    public StmtNode thenStmt;
    public StmtNode elseStmt;

    public branchStmtNode(position pos, ExprNode cond,
                          StmtNode thenStmt, StmtNode elseStmt) {
        super(pos);
        this.cond = cond;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "branchStmtNode";
    }
}
