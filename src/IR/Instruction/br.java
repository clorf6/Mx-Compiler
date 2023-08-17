package IR.Instruction;

import IR.IRVisitor;
import IR.Entity.Entity;
import IR.Type.*;
import Utils.Error.internalError;
import Utils.Position;

import java.util.HashMap;

public class br extends Instruction {

    public Entity cond;
    public labelType iftrue, iffalse;

    public br(Entity cond, labelType iftrue, labelType iffalse, HashMap<labelType, Boolean> appear, Position pos) {
        this.cond = cond;
        this.iftrue = iftrue;
        this.iffalse = iffalse;
        if (!(cond.type instanceof integerType)) {
            throw new internalError(pos, "Br instruction cond type wrong");
        } else {
            if (((integerType) cond.type).bit != 1) {
                throw new internalError(pos, "Br instruction cond type wrong");
            }
        }
        appear.put(iftrue, true);
        appear.put(iffalse, true);
    }

    public br(labelType dest, HashMap<labelType, Boolean> appear) {
        this.iftrue = dest;
        this.cond = null;
        this.iffalse = null;
        appear.put(iftrue, true);
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
