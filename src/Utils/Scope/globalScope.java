package Utils.Scope;

import AST.Type.Type;
import Utils.Error.*;
import Utils.Position;

import java.util.HashMap;

public class globalScope extends Scope {
    public HashMap<String, Type> basic = new HashMap<>();

    public globalScope(Scope fatherScope) {
        super(fatherScope);
        this.dep = 0;
    }

    public void putBasic(String name, Type type, Position pos) {
        if (basic.containsKey(name))
            throw new semanticError(pos, "multiple definition of " + name);
        basic.put(name, type);
    }

    public Type getBasic(String name) {
        return basic.getOrDefault(name, null);
    }
}
