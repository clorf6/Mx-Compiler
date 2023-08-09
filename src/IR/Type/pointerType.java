package IR.Type;

public class pointerType extends Type {
    public Type elemType;

    public pointerType(Type elemType) {
        this.elemType = elemType;
    }

    @Override
    public int size() {
        return 4;
    }

    @Override
    public String toString() {
        return "ptr";
    }
}
