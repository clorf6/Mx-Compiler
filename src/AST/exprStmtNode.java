package AST;

import Utils.position;
import java.util.ArrayList;

public class exprStmtNode extends StmtNode {
    ArrayList<ExprNode> expr;

    public exprStmtNode(position pos, ArrayList<ExprNode> expr) {
        super(pos);
        this.expr = expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "exprStmtNode";
    }
}
