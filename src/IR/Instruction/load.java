package IR.Instruction;

import IR.IRVisitor;
import IR.Entity.Entity;
import IR.Type.*;
import Utils.Error.internalError;
import Utils.Position;

public class load extends Instruction {
    public Entity res, p;

    public load(Entity res, Entity p, Position pos) {
        this.res = res;
        this.p = p;
        if (!(p.type instanceof pointerType)) {
            throw new internalError(pos, "Load instruction pointer type wrong");
        }
        if (!res.type.getClass().equals(((pointerType) p.type).elemType.getClass())) {
            throw new internalError(pos, "Load instruction result type wrong");
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
