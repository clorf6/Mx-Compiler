package IR.Object;

import IR.Type.*;
import Utils.Error.internalError;

public class boolObject extends constObject {
    public boolean val;

    public boolObject(Type type) {
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

    public boolObject(Type type, boolean val) {
        this(type);
        this.val = val;
    }

    public boolObject(boolean val) {
        super(new integerType(1));
        this.val = val;
    }

    @Override
    public String getText() {
        return Boolean.toString(val);
    }

    @Override
    public String toString() {
        return "i1 " + getText();
    }
}
