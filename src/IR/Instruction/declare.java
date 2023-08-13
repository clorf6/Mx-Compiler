package IR.Instruction;

import IR.IRVisitor;
import IR.Type.Type;
import IR.Entity.localVarEntity;

import java.util.ArrayList;

public class declare extends Instruction {
    public String name;
    public Type retType;
    public ArrayList<localVarEntity> param;

    public declare(String name, Type retType, ArrayList<localVarEntity> param) {
        this.name = name;
        this.retType = retType;
        this.param = param;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder("declare " + retType.toString() + " @" + name + "(");
        for (int i = 0; i < param.size(); i++) {
            ret.append(param.get(i).toString());
            if (i != param.size() - 1) {
                ret.append(", ");
            }
        };
        ret.append(")");
        return ret.toString();
    }
}
