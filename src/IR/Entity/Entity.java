package IR.Entity;

import IR.Type.*;

public abstract class Entity {
    public Type type;

    public Entity(Type type) {
        this.type = type;
    }

    public abstract String getText();
    public abstract String toString();
}
