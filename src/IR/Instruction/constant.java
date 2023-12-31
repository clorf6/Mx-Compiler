package IR.Instruction;

import IR.Entity.*;
import IR.IRVisitor;

public class constant extends Instruction {

    public globalVarEntity var;
    public constEntity init;

    public constant(globalVarEntity var, constEntity init) {
        this.var = var;
        this.init = init;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        String ret = var.getText() + " = constant ";
        if (init != null) {
            ret += init.toString();
        } else {
            ret += var.type.toString() + " zeroinitializer";
        }
        return ret;
    }
}
