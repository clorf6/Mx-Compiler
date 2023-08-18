package IR.Entity;

import IR.Type.*;
import Utils.Error.internalError;
import Utils.Position;

public class stringEntity extends constEntity {
    public String val;

    public stringEntity(Type type) {
        super(type);
        if (!(type instanceof arrayType)) {
            throw new internalError(new Position(0, 0), "String type wrong");
        } else {
            if (!(((arrayType) type).elemType instanceof integerType)) {
                throw new internalError(new Position(0, 0), "String type wrong");
            } else {
                if (((integerType) ((arrayType) type).elemType).bit != 8) {
                    throw new internalError(new Position(0, 0), "String type wrong");
                } else {
                    val = null;
                }
            }
        }
    }

    public stringEntity(Type type, String val) {
        this(type);
        this.val = val;
        if (((arrayType) type).length != val.length() + 1) {
            throw new internalError(new Position(0, 0), "String type wrong");
        }
    }

    public stringEntity(String val) {
        this(new arrayType(new integerType(8), val.length() + 1));
        this.val = val;
    }

    public stringEntity(String val, int length) {
        this(new arrayType(new integerType(8), length));
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