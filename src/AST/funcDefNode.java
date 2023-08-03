package AST;

import Utils.position;

import java.util.ArrayList;

public class funcDefNode extends ASTNode {
    public typeNode typename;
    public String name;
    public ArrayList<paramNode> param;
    public blockStmtNode suite;

    public funcDefNode(position pos, typeNode typename, String name,
                       ArrayList<paramNode> param, blockStmtNode suite) {
        super(pos);
        this.typename = typename;
        this.name = name;
        this.param = param;
        this.suite = suite;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "funcDefNode";
    }
}
