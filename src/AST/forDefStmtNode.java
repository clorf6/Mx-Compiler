package AST;

import Utils.position;
public class forDefStmtNode extends loopStmtNode {
    public varDefNode varDef;
    public ExprNode cond;
    public ExprNode step;
    public StmtNode stmt;

    public forDefStmtNode(position pos, varDefNode varDef,
                          ExprNode cond, ExprNode step, StmtNode stmt) {
        super(pos);
        this.varDef = varDef;
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
        return "forDefStmtNode";
    }
}
