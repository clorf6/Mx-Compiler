package AST;

import Utils.position;

public abstract class loopStmtNode extends StmtNode {
    public loopStmtNode(position pos) {
        super(pos);
    }

    @Override
    public String toString() {
        return "loopStmtNode";
    }
}