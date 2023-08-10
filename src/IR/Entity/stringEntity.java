package IR.Entity;

import IR.Type.*;
import Utils.Error.internalError;

public class stringEntity extends constEntity {
    String val;

    public stringEntity(Type type) {
        super(type);
        if (!(type instanceof arrayType)) {
            throw new internalError(null, "String type wrong");
        } else {
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

    public stringEntity(Type type, String val) {
        this(type);
        this.val = val;
        if (((arrayType) type).length != val.length()) {
            throw new internalError(null, "String type wrong");
        }
    }

    public stringEntity(String val) {
        this(new arrayType(new integerType(8), val.length()));
        this.val = val;
    }

    @Override
    public String getText() {
        return "c\"" + val + "\\00\"";
    }

    @Override
    public String toString() {
        return type.toString() + " " + getText();
    }
}