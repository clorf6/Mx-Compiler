package IR.Instruction;

import IR.IRVisitor;
import IR.Entity.Entity;
import IR.Type.*;
import Utils.Error.internalError;
import Utils.Position;

public class store extends Instruction {
    public Entity val, p;

    public store(Entity val, Entity p, Position pos) {
        this.val = val;
        this.p = p;
        if (!(p.type instanceof pointerType)) {
            throw new internalError(pos, "Store instruction pointer type wrong");
        }
        if ((((pointerType) p.type).elemType instanceof pointerType)) {
            if (((pointerType) ((pointerType) p.type).elemType).elemType instanceof voidType) {
                if (!(val.type instanceof classType || val.type instanceof arrayType)) {
                    throw new internalError(pos, "Store instruction result type wrong");
                }
            } else {
                if (!val.type.getClass().equals(((pointerType) p.type).elemType.getClass())) {
                    throw new internalError(pos, "Store instruction result type wrong");
                }
            }
        } else {
            if (!val.type.getClass().equals(((pointerType) p.type).elemType.getClass())) {
                throw new internalError(pos, "Store instruction result type wrong");
            }
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
