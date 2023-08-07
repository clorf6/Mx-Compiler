package AST;

import Utils.Position;
import java.util.ArrayList;

public class exprStmtNode extends StmtNode {
    public ArrayList<ExprNode> expr;

    public exprStmtNode(Position pos, ArrayList<ExprNode> expr) {
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
