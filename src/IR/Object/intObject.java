package IR.Object;

import IR.Type.*;
import Utils.Error.internalError;

public class intObject extends constObject {
    public int val;

    public intObject(Type type) {
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

    public intObject(Type type, int val) {
        this(type);
        this.val = val;
    }

    public intObject(int val) {
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

    public static class nullType extends Type {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public String toString() {
            return "null";
        }
    }
}
