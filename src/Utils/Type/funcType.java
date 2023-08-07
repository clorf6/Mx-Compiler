package Utils.Type;

import AST.funcDefNode;

public class funcType extends Type {
    public funcDefNode func;
    public funcType(funcDefNode func) {
        super("func");
        this.func = func;
    }

    @Override
    public boolean equal(Object obj) {
        return obj.getClass().equals(funcType.class)
                && ((funcType) obj).func.equals(func);
    }

    @Override
    public String toString() {
        return "func";
    }
}
