package IR.Instruction;

import IR.Entity.Entity;
import IR.Type.pointerType;
import IR.IRVisitor;
import Utils.Error.internalError;
import Utils.Position;

public class bitcast extends Instruction {
    Entity res, now;

    public bitcast(Entity res, Entity now, Position pos) {
        this.res = res;
        this.now = now;
        if (!(res.type instanceof pointerType && now.type instanceof pointerType)) {
            throw new internalError(pos, "Bitcast Instruction Wrong");
        }
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return res.getText() + " = bitcast " + ((pointerType) now.type).getText() +
                " " + now.getText() + " to " + ((pointerType) res.type).getText();
    }
}
