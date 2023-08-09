package AST.Type;

public class voidType extends Type {
    public voidType() {
        super("void");
    }

    @Override
    public boolean equal(Object obj) {
        return obj.getClass().equals(voidType.class);
    }

    @Override
    public String toString() {
        return "void";
    }
}
