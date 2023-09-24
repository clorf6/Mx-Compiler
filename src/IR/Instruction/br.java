package IR.Instruction;

import IR.IRVisitor;
import IR.Entity.Entity;
import IR.Type.*;
import IR.Block;
import Utils.Error.internalError;
import Utils.Position;

import java.util.HashMap;
import java.util.Objects;

public class br extends Instruction {

    public Entity cond;
    public labelType iftrue, iffalse;

    public br(Entity cond, Block iftrue, Block iffalse, Block p, Position pos) {
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
        Block pre = Objects.equals(p.name.name, "begin") ? p.pre.get(0) : p;
        pre.suc.add(iftrue);
        pre.suc.add(iffalse);
        iftrue.pre.add(pre);
        iffalse.pre.add(pre);
    }

    public br(Block dest, Block p) {
        this.iftrue = dest.name;
        Block pre = Objects.equals(p.name.name, "begin") ? p.pre.get(0) : p;
        dest.pre.add(pre);
        pre.suc.add(dest);
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
