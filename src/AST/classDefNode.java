package AST;

import Utils.Position;

import java.util.ArrayList;

public class classDefNode extends ASTNode {
    public String name;
    public ArrayList<varDefNode> varDef;

    public ArrayList<funcDefNode> funcDef;

    public classDefNode(Position pos, String name,
                        ArrayList<varDefNode> varDef,
                        ArrayList<funcDefNode> funcDef) {
        super(pos);
        this.name = name;
        this.varDef = varDef;
        this.funcDef = funcDef;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "classDefNode";
    }
}
