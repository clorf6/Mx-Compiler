package AST;

import Utils.Position;

import java.util.ArrayList;

public class branchStmtNode extends StmtNode {
    public ArrayList<ExprNode> cond;
    public ArrayList<StmtNode> Stmt;

    public branchStmtNode(Position pos, ArrayList<ExprNode> cond, ArrayList<StmtNode> Stmt) {
        super(pos);
        this.cond = cond;
        this.Stmt = Stmt;
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
