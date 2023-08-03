package Utils.Type;

public class funcType extends Type {
    public String funcName;

    public funcType(String funcName) {
        super("func");
        this.funcName = funcName;
    }

    @Override
    public boolean equal(Object obj) {
        return obj.getClass().equals(funcType.class)
                && ((funcType) obj).funcName.equals(funcName);
    }

    @Override
    public String toString() {
        return "func:" + funcName;
    }
}
