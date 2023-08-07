package AST;

import Utils.*;

public abstract class StmtNode extends ASTNode {
    public StmtNode(Position pos) {
        super(pos);
    }

    @Override
    public String toString() {
        return "StmtNode";
    }
}
