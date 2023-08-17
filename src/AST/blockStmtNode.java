package AST;

import Utils.*;

import java.util.ArrayList;

public class blockStmtNode extends StmtNode {
    public ArrayList<StmtNode> stmt;

    public boolean newScope;
    public blockStmtNode(Position pos, ArrayList<StmtNode> stmt) {
        super(pos);
        this.stmt = stmt;
        this.newScope = true;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "blockStmtNode";
    }
}
