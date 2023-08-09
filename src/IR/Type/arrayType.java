package IR.Type;

public class arrayType extends Type {
    public Type elemType;
    public int length;

    public arrayType(Type elemType, int length) {
        this.elemType = elemType;
        this.length = length;
    }

    @Override
    public int size() {
        return length * elemType.size();
    }

    @Override
    public String toString() {
        return "[" + length + " x " + elemType.toString() + "]";
    }
}
