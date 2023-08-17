package IR;

import IR.Entity.localVarEntity;
import IR.Instruction.Instruction;
import IR.Type.Type;
import IR.Type.labelType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

public class Function {
    public String name;
    public Type retType;
    public ArrayList<localVarEntity> param;
    public LinkedList<Block> block;

    public Function(String name, Type retType, ArrayList<localVarEntity> param) {
        this.name = name;
        this.retType = retType;
        this.param = param;
        this.block = new LinkedList<>();
        block.add(new Block("entry"));
        block.add(new Block("begin"));
    }

    public void add(Block bl, HashMap<labelType, Boolean> appear) {
        if (appear.get(bl.name) != null) {
            block.add(bl);
        }
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
