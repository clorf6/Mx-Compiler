package IR.Object;

import IR.Type.*;
import Utils.Error.internalError;

public class stringObject extends constObject {
    String val;

    public stringObject(Type type) {
        super(type);
        if (!(type instanceof arrayType)) {
            throw new internalError(null, "String type wrong");
        } else {
            if (((arrayType) type).length != val.length()) {
                throw new internalError(null, "String type wrong");
            }
            if (!(((arrayType) type).elemType instanceof integerType)) {
                throw new internalError(null, "String type wrong");
            } else {
                if (((integerType) ((arrayType) type).elemType).bit != 8) {
                    throw new internalError(null, "String type wrong");
                } else {
                    val = null;
                }
            }
        }
    }

    public stringObject(Type type, String val) {
        this(type);
        this.val = val;
    }

    public stringObject(String val) {
        this(new arrayType(new integerType(8), val.length()));
        this.val = val;
    }

    @Override
    public String getText() {
        return "c\"" + val + "\\00\"";
    }

    @Override
    public String toString() {
        
    }
}