package AST;

import Utils.Position;

public abstract class flowStmtNode extends StmtNode {
    public flowStmtNode(Position pos) {
        super(pos);
    }

    @Override
    public String toString() {
        return "flowStmtNode";
    }
}