package AST;

import Utils.position;
import Utils.Type.*;

public class typeNode extends ASTNode {
    public Type type;

    public typeNode(position pos, Type type) {
        super(pos);
        this.type = type;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "typenameNode";
    }

}
