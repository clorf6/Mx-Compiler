package AST;

import Utils.*;

public abstract class StmtNode extends ASTNode {
    public StmtNode(position pos) {
        super(pos);
    }

    @Override
    public String toString() {
        return "StmtNode";
    }
}
