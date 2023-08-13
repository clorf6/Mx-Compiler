package AST;

import Utils.*;
import java.util.ArrayList;

public class varDefNode extends defNode {
    public typeNode typename;
    public ArrayList<varNode> var;

    public varDefNode(Position pos, typeNode typename, ArrayList<varNode> var) {
        super(pos);
        this.typename = typename;
        this.var = var;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "varDefNode";
    }
}
