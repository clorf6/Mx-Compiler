package IR.Instruction;

import IR.IRVisitor;
import IR.Object.Object;
import IR.Type.*;
import Utils.Error.internalError;

public class load extends Instruction {
    public Object res, p;

    public load(Object res, Object p) {
        this.res = res;
        this.p = p;
        if (!(p.type instanceof pointerType)) {
            throw new internalError(null, "Load instruction pointer type wrong");
        }
        if (!res.type.getClass().equals(((pointerType) p.type).elemType.getClass())) {
            throw new internalError(null, "Load instruction result type wrong");
        }
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return res.getText() + " = load " + res.type.toString()
                + ", ptr " + p.getText();
    }
}
