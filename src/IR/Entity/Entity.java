package IR.Entity;

import IR.Type.*;

public abstract class Entity {
    public Type type;

    public Entity(Type type) {
        this.type = type;
    }

    public abstract String getText();
    public abstract String toString();

    public boolean isString() {
        if (!(type instanceof pointerType)) return false;
        else {
            if (((pointerType) type).elemType instanceof integerType) {
                return ((integerType) ((pointerType) type).elemType).bit == 8;
            } else if ((((pointerType) type).elemType instanceof arrayType))  {
                if (((arrayType) ((pointerType) type).elemType).elemType instanceof integerType) {
                    return ((integerType) ((arrayType) ((pointerType) type).elemType).elemType).bit == 8;
                } else return false;
            } else return false;
        }
    }
}
