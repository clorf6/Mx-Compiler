package Utils.Type;

public class intType extends Type {
    public intType() {
        super("int");
    }

    @Override
    public boolean equal(Object obj) {
        return obj.getClass().equals(intType.class);
    }

    @Override
    public String toString() {
        return "int";
    }
}

