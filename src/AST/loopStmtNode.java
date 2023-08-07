package AST;

import Utils.Position;

public abstract class loopStmtNode extends StmtNode {
    public loopStmtNode(Position pos) {
        super(pos);
    }

    @Override
    public String toString() {
        return "loopStmtNode";
    }
}