package AST;

import Utils.position;

public abstract class flowStmtNode extends StmtNode {
    public flowStmtNode(position pos) {
        super(pos);
    }

    @Override
    public String toString() {
        return "flowStmtNode";
    }
}