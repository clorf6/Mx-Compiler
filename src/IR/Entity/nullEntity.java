package IR.Entity;

import IR.Type.*;
import Utils.Error.internalError;
import Utils.Position;

public class nullEntity extends constEntity {
    public nullEntity(Type type) {
        super(type);
        if (!(type instanceof pointerType)) {
            throw new internalError(new Position(0, 0), "Null type wrong");
        }
    }

    public nullEntity() {
        super(new pointerType(new voidType()));
    }

    @Override
    public String getText() {
        return "null";
    }

    @Override
    public String toString() {
        return type.toString() + " " + getText();
    }
}
