package Utils.Type;

public abstract class Type {
    public String typeName;

    public Type(String typeName) {
        this.typeName = typeName;
    }
    public abstract boolean equal(Object obj);

    public abstract String toString();
}