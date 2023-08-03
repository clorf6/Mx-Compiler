package AST;

import Utils.*;

import java.util.ArrayList;

public class rootNode extends ASTNode{
    public ArrayList<funcDefNode> funcDef;
    public ArrayList<classDefNode> classDef;
    public ArrayList<varDefNode> varDef;

    public rootNode(position pos, ArrayList<funcDefNode> funcDef,
                    ArrayList<classDefNode> classDef, ArrayList<varDefNode> varDef) {
        super(pos);
        this.classDef = classDef;
        this.funcDef = funcDef;
        this.varDef = varDef;
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
