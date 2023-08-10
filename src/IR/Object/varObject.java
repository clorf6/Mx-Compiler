package IR.Object;

import IR.Type.*;

public abstract class varObject extends Object {
    public String name;

    public varObject(Type type, String name) {
        super(type);
        this.name = name;
    }
}