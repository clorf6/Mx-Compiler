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

    public String getText() {
        int num = 1;
        while (elemType instanceof pointerType) {
            num++;
            elemType = ((pointerType) elemType).elemType;
        }
        StringBuilder ret = new StringBuilder(elemType.toString());
        for (int i = 1; i <= num; ++i) ret.append("*");
        return ret.toString();
    }

    @Override
    public String toString() {
        return "ptr";
    }
}
