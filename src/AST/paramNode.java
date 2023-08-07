package AST;

import Utils.Position;

public class paramNode extends ASTNode {
    public typeNode typename;
    public String name;
    public paramNode(Position pos, typeNode typename, String name) {
        super(pos);
        this.typename = typename;
        this.name = name;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "paramNode";
    }
}
