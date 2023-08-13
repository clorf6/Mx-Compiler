package AST;

import Utils.Position;

public abstract class defNode extends ASTNode {
    public defNode(Position pos) {
        super(pos);
    }

    @Override
    public String toString() {
        return "defNode";
    };
}
