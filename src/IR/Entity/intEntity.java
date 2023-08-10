package IR.Entity;

import IR.Type.*;
import Utils.Error.internalError;

public class intEntity extends constEntity {
    public int val;

    public intEntity(Type type) {
        super(type);
        if (!(type instanceof integerType)) {
            throw new internalError(null, "Bool type wrong");
        } else {
            if (((integerType) type).bit != 32) {
                throw new internalError(null, "Bool type wrong");
            } else {
                val = 0;
            }
        }
    }

    public intEntity(Type type, int val) {
        this(type);
        this.val = val;
    }

    public intEntity(int val) {
        super(new integerType(32));
        this.val = val;
    }

    @Override
    public String getText() {
        return Integer.toString(val);
    }

    @Override
    public String toString() {
        return "i32 " + getText();
    }
}
