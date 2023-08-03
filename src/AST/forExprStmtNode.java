package AST;

import Utils.position;
public class forExprStmtNode extends loopStmtNode {
    public ExprNode init;
    public ExprNode cond;
    public ExprNode step;
    public StmtNode stmt;

    public forExprStmtNode(position pos, ExprNode init,
                          ExprNode cond, ExprNode step, StmtNode stmt) {
        super(pos);
        this.init = init;
        this.cond = cond;
        this.step = step;
        this.stmt = stmt;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "forExprStmtNode";
    }
}
