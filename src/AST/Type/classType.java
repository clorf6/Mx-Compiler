package AST.Type;

import java.util.ArrayList;

public class classType extends Type {
    public String className;
    public ArrayList<Type> vars;

    public classType(String className) {
        super("class");
        this.className = className;
        this.vars = new ArrayList<>();
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
