package AST.Type;

public class boolType extends Type {
    public boolType() {
        super("bool");
    }

    @Override
    public boolean equal(Object obj) {
        return obj.getClass().equals(boolType.class);
    }

    @Override
    public String toString() {
        return "bool";
    }
}