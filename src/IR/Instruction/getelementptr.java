package IR.Instruction;

import IR.IRVisitor;
import IR.Entity.Entity;
import IR.Entity.intEntity;
import IR.Type.*;
import Utils.Error.internalError;
import Utils.Position;

import java.util.ArrayList;

public class getelementptr extends Instruction {
    public Entity res, p;
    public ArrayList<Entity> idx;

    public getelementptr(Entity res, Entity p, ArrayList<Entity> idx, Position pos) {
        this.res = res;
        this.p = p;
        this.idx = idx;
        if (!(res.type instanceof pointerType)) {
            throw new internalError(pos, "Getelementptr instruction result type wrong");
        }
        if (!(p.type instanceof pointerType)) {
            throw new internalError(pos, "Getelementptr instruction pointer type wrong");
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
        for (Entity Idx : idx) {
            ret.append(", ").append(Idx.toString());
        }
        return ret.toString();
    }
}
