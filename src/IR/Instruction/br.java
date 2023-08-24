package IR.Instruction;

import IR.IRVisitor;
import IR.Entity.Entity;
import IR.Type.*;
import IR.Block;
import Utils.Error.internalError;
import Utils.Position;

import java.util.HashMap;

public class br extends Instruction {

    public Entity cond;
    public labelType iftrue, iffalse;

    public br(Entity cond, Block iftrue, Block iffalse, Block pre, Position pos) {
        this.cond = cond;
        this.iftrue = iftrue.name;
        this.iffalse = iffalse.name;
        if (!(cond.type instanceof integerType)) {
            throw new internalError(pos, "Br instruction cond type wrong");
        } else {
            if (((integerType) cond.type).bit != 1) {
                throw new internalError(pos, "Br instruction cond type wrong");
            }
        }
        iftrue.pre.add(pre);
        iffalse.pre.add(pre);
    }

    public br(Block dest, Block pre) {
        this.iftrue = dest.name;
        dest.pre.add(pre);
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
