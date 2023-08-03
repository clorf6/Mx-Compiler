package AST;

import Utils.*;
import java.util.ArrayList;

public class funcExprNode extends ExprNode {
    String name;
    ArrayList<ExprNode> args;

    public funcExprNode(position pos, ArrayList<ExprNode> args, String name) {
        super(pos);
        this.args = args;
        this.name = name;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "funcExprNode";
    }
}
