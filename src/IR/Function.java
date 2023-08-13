package IR;

import IR.Entity.localVarEntity;
import IR.Type.Type;

import java.util.ArrayList;

public class Function {
    public String name;
    public Type retType;
    public ArrayList<localVarEntity> param;
    public ArrayList<Block> block;

    public Function(String name, Type retType, ArrayList<localVarEntity> param) {
        this.name = name;
        this.retType = retType;
        this.param = param;
        this.block = new ArrayList<>();
        block.add(new Block("entry"));
    }

    public void add(Block bl) {
        block.add(bl);
    }

    public String toString() {
        StringBuilder ret = new StringBuilder("define " + retType.toString() + " @" + name + "(");
        for (int i = 0; i < param.size(); i++) {
            ret.append(param.get(i).toString());
            if (i != param.size() - 1) {
                ret.append(", ");
            }
        }
        ret.append(") {\n");
        for (Block bl : block) {
            ret.append(bl.toString());
        }
        ret.append("}\n");
        return ret.toString();
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
