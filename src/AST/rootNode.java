package AST;

import Utils.*;

import java.util.ArrayList;

public class rootNode extends ASTNode{
    public ArrayList<defNode> Def;

    public rootNode(Position pos, ArrayList<defNode> Def) {
        super(pos);
        this.Def = Def;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "rootNode";
    }
}
