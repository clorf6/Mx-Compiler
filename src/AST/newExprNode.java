package AST;

import Utils.*;
import AST.Type.*;

import java.util.ArrayList;

public class newExprNode extends ExprNode {
    public Type typename;
    public ArrayList<ExprNode> expr;
    public int dim;
    public newExprNode(Position pos, Type typename, ArrayList<ExprNode> expr, int dim) {
        super(pos);
        this.typename = typename;
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
