package IR.Instruction;

import IR.IRVisitor;
import IR.Object.Object;
import IR.Type.*;
import Utils.Error.internalError;

public class br extends Instruction {

    public Object cond;
    public labelType iftrue, iffalse;

    public br(Object cond, labelType iftrue, labelType iffalse) {
        this.cond = cond;
        this.iftrue = iftrue;
        this.iffalse = iffalse;
        if (!(cond.type instanceof integerType)) {
            throw new internalError(null, "Br instruction cond type wrong");
        } else {
            if (((integerType) cond.type).bit != 1) {
                throw new internalError(null, "Br instruction cond type wrong");
            }
        }
    }

    public br(labelType dest) {
        this.iftrue = dest;
        this.cond = null;
        this.iffalse = null;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        if (cond != null) {
            return "br " + cond + ", " + iftrue.toString()
                    + ", " + iffalse.toString();
        } else {
            return "br " + iftrue.toString();
        }
    }

}
