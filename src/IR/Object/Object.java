package IR.Object;

import IR.Type.*;

public abstract class Object {
    public Type type;

    public Object(Type type) {
        this.type = type;
    }

    public abstract String getText();
    public abstract String toString();
}
