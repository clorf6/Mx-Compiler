package AST.Type;

public class nullType extends Type {
    public nullType() {
        super("null");
    }

    @Override
    public boolean equal(Object obj) {
        return obj.getClass().equals(nullType.class);
    }

    @Override
    public String toString() {
        return "null";
    }
}
