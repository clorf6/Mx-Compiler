package IR.Object;

import IR.Type.*;
import Utils.Error.internalError;

public class nullObject extends constObject {
    public nullObject(Type type) {
        super(type);
        if (!(type instanceof pointerType)) {
            throw new internalError(null, "Null type wrong");
        }
    }

    @Override
    public String getText() {
        return "null";
    }

    @Override
    public String toString() {
        return getText();
    }
}
