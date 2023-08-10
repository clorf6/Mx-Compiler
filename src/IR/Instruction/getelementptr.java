package IR.Instruction;

import IR.IRVisitor;
import IR.Object.Object;
import IR.Object.intObject;
import IR.Type.*;
import Utils.Error.internalError;

import java.util.ArrayList;

public class getelementptr extends Instruction {
    public Object res, p;
    public ArrayList<intObject> idx;

    public getelementptr(Object res, Object p, ArrayList<intObject> idx) {
        this.res = res;
        this.p = p;
        this.idx = idx;
        if (!(res.type instanceof pointerType)) {
            throw new internalError(null, "Getelementptr instruction result type wrong");
        }
        if (!(p.type instanceof pointerType)) {
            throw new internalError(null, "Getelementptr instruction pointer type wrong");
        }
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder(res.getText() + " = getelementptr " +
                ((pointerType) p.type).elemType.toString() + ", " +
                p.toString());
        for (intObject Idx : idx) {
            ret.append(", ").append(Idx.toString());
        }
        return ret.toString();
    }
}
