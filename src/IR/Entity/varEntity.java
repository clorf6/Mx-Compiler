package IR.Entity;

import IR.Type.*;

public abstract class varEntity extends Entity {
    public String name;
    public boolean member;
    public varEntity(Type type, String name) {
        super(type);
        this.name = name;
        this.member = false;
    }
}