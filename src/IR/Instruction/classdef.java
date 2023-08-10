package IR.Instruction;

import IR.IRVisitor;
import IR.Type.*;

public class classdef extends Instruction {
    classType name;

    public classdef(classType name) {
        this.name = name;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder(name.toString() + " = type { ");
        for (int i = 0; i < name.typelist.size(); i++) {
            ret.append(name.typelist.get(i).toString());
            if (i != name.typelist.size() - 1) {
                ret.append(", ");
            }
        }
        ret.append("}");
        return ret.toString();
    }
}
