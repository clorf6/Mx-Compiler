package AST;

import Utils.*;
import Utils.Type.*;

import java.util.ArrayList;

public class newExprNode extends ExprNode {
    public Type type;
    public ArrayList<ExprNode> expr;
    public int dim;
    public newExprNode(position pos, Type type, ArrayList<ExprNode> expr, int dim) {
        super(pos);
        this.type = type;
        this.expr = expr;
        this.dim = dim;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "newExprNode";
    }
}
