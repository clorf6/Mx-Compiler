package Utils.Type;

public class arrayType extends Type {
    public Type elemType;

    public arrayType(Type elemType) {
        super("array");
        this.elemType = elemType;
    }

    @Override
    public boolean equal(Object obj) {
        return obj.getClass().equals(arrayType.class)
                && ((arrayType) obj).elemType.equals(elemType);
    }

    @Override
    public String toString() {
        return "array:" + elemType.toString();
    }
}
