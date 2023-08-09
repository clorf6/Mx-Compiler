package AST.Type;

public class stringType extends Type {
    public stringType() {
        super("string");
    }

    @Override
    public boolean equal(Object obj) {
        return obj.getClass().equals(stringType.class);
    }

    @Override
    public String toString() {
        return "string";
    }
}
