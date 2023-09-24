package IR.Instruction;

import IR.Entity.*;
import IR.IRVisitor;

public class global extends Instruction {

    public globalVarEntity var;
    public Entity init;

    public global(globalVarEntity var, Entity init) {
        this.var = var;
        this.init = init;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        String ret = var.getText() + " = global ";
        if (init != null) {
            ret += init.toString();
        } else {
            ret += var.type.toString() + " zeroinitializer";
        }
        return ret;
    }
}
