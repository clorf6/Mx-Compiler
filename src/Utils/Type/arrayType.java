package Utils.Type;

public class arrayType extends Type {
    public Type elemType;
    public int dim;

    public arrayType(Type elemType, int dim) {
        super("array");
        this.elemType = elemType;
        this.dim = dim;
    }

    @Override
    public boolean equal(Object obj) {
        return obj.getClass().equals(arrayType.class)
                && ((arrayType) obj).elemType.getClass().equals(elemType.getClass())
                && ((arrayType) obj).dim == dim;
    }

    @Override
    public String toString() {
        return "array:" + elemType.toString() + "[" + dim + "]";
    }
}
