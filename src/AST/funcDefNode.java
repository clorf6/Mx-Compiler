package AST;

import Utils.Position;

import java.util.ArrayList;

public class funcDefNode extends defNode {
    public typeNode typename;
    public String name;
    public ArrayList<paramNode> param;
    public blockStmtNode suite;

    public funcDefNode(Position pos, typeNode typename, String name,
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
