package IR.Entity;

import IR.Type.*;
import Utils.Error.internalError;

public class boolEntity extends constEntity {
    public boolean val;

    public boolEntity(Type type) {
        super(type);
        if (!(type instanceof integerType)) {
            throw new internalError(null, "Bool type wrong");
        } else {
            if (((integerType) type).bit != 1) {
                throw new internalError(null, "Bool type wrong");
            } else {
                val = false;
            }
        }
    }

    public boolEntity(Type type, boolean val) {
        this(type);
        this.val = val;
    }

    public boolEntity(boolean val) {
        super(new integerType(1));
        this.val = val;
    }

    @Override
    public String getText() {
        return val ? "1" : "0";
    }

    @Override
    public String toString() {
        return "i1 " + getText();
    }
}
