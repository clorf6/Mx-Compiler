package IR.Instruction;

import IR.IRVisitor;
import IR.Type.*;
import IR.Entity.Entity;
import Utils.Error.internalError;
import Utils.Position;

public class alloca extends Instruction {
    Type type;
    Entity res;

    public alloca(Entity res, Type type, Position pos) {
        this.type = type;
        this.res = res;
        if (!(res.type instanceof pointerType)) {
            throw new internalError(pos, "Alloca instruction result type wrong");
        }
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return res.getText() + " = alloca " + type.toString();
    }
}
