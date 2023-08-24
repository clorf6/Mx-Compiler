package IR.Type;

import java.util.ArrayList;
import java.util.HashMap;

public class classType extends Type {
    public String name;
    int size;
    public ArrayList<Type> typelist;
    public HashMap<String, Integer> pos;
    public HashMap<Integer, Integer> offset;

    public classType(String name, ArrayList<Type> typelist) {
        this.name = name;
        this.typelist = typelist;
        for (Type type : typelist) {
            size += type.size();
        }
        pos = new HashMap<>();
        offset = new HashMap<>();
    }

    public void flush() {
        size = 0;
        for (Type type : typelist) {
            size += type.size();
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        return "%class." + name;
    }
}
