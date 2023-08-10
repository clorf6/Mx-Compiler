package IR.Instruction;

import IR.IRVisitor;
import IR.Object.Object;
import IR.Type.*;
import Utils.Error.internalError;

public class store extends Instruction {
    public Object val, p;

    public store(Object val, Object p) {
        this.val = val;
        this.p = p;
        if (!(p.type instanceof pointerType)) {
            throw new internalError(null, "Load instruction pointer type wrong");
        }
        if (!val.type.getClass().equals(((pointerType) p.type).elemType.getClass())) {
            throw new internalError(null, "Load instruction result type wrong");
        }
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "store " + val.toString() + ", ptr " + p.getText();
    }
}
