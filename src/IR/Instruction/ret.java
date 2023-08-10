package IR.Instruction;

import IR.Object.Object;
import IR.Type.voidType;
import IR.IRVisitor;

public class ret extends Instruction {
    Object val;

    public ret(Object val) {
        this.val = val;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        if (val.type instanceof voidType) {
            return "ret void";
        } else {
            return "ret " + val;
        }
    }
}
