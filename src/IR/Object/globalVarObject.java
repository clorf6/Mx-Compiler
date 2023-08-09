package IR.Object;

import IR.Type.*;

public class globalVarObject extends varObject {

    public globalVarObject(Type type, String name) {
        super(type, name);
    }

    @Override
    public String getText() {
        return "@" + name;
    }

    @Override
    public String toString() {
        return type.toString() + " " + getText();
    }
}
