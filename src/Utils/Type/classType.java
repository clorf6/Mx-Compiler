package Utils.Type;

import AST.classDefNode;
import AST.funcDefNode;
import AST.varNode;
import AST.varDefNode;
import java.util.HashMap;

public class classType extends Type {
    public String className;

    public classType(String className) {
        super("class");
        this.className = className;
    }

    @Override
    public boolean equal(Object obj) {
        return obj.getClass().equals(classType.class)
                && ((classType) obj).className.equals(className);
    }

    @Override
    public String toString() {
        return "class";
    }
}
