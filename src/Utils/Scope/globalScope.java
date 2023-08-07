package Utils.Scope;

import Utils.Error.*;
import Utils.Position;
import Utils.Type.*;

import java.util.HashMap;

public class globalScope extends Scope {
    public HashMap<String, Type> basic = new HashMap<>();

    public globalScope(Scope fatherScope) {
        super(fatherScope);
    }

    public void putBasic(String name, Type type, Position pos) {
        if (basic.containsKey(name))
            throw new semanticError(pos, "multiple definition of " + name);
        basic.put(name, type);
    }

    public Type getBasic(String name, Position pos) {
        if (basic.containsKey(name)) return basic.get(name);
        else return null;
        //throw new semanticError(pos, "no such type: " + name);
    }
}
