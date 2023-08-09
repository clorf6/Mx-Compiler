package IR.Type;

import jdk.jfr.Percentage;

public class labelType extends Type {
    public String name;

    public labelType(String name) {
        this.name = name;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public String toString() {
        return "label %" + name;
    }
}
