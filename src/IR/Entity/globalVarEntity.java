package IR.Entity;

import IR.Type.*;

public class globalVarEntity extends varEntity {

    public globalVarEntity(Type type, String name) {
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
