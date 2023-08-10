package IR;

import IR.Entity.Entity;
import IR.Type.Type;

import java.util.ArrayList;

public class Function {
    public String name;
    public Type retType;
    public ArrayList<Entity> param;
    public ArrayList<Block> block;

    public Function(String name, Type retType, ArrayList<Entity> param) {
        this.name = name;
        this.retType = retType;
        this.param = param;
        this.block = new ArrayList<>();
        block.add(new Block("entry"));
    }

    public void add(Block bl) {
        block.add(bl);
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
