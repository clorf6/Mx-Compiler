package Utils.Scope;

import AST.Type.Type;
import Utils.Error.*;
import Utils.Position;
import IR.Function;
import IR.Type.classType;

import java.util.HashMap;

public class globalScope extends Scope {
    public HashMap<String, Type> basic;

    public HashMap<String, Function> funcs;
    public HashMap<String, classType> classes;

    public HashMap<String, Integer> BuiltinFunc;

    public globalScope(Scope fatherScope) {
        super(fatherScope);
        this.basic = new HashMap<>();
        this.funcs = new HashMap<>();
        this.BuiltinFunc = new HashMap<>();
        this.classes = new HashMap<>();
    }

    public void putBasic(String name, Type type, Position pos) {
        if (basic.containsKey(name))
            throw new semanticError(pos, "multiple definition of " + name);
        basic.put(name, type);
    }

    public Type getBasic(String name) {
        return basic.getOrDefault(name, null);
    }


    public void putFunc(String name, Function func, Position pos) {
        if (funcs.containsKey(name))
            throw new internalError(pos, "multiple definition of " + name);
        funcs.put(name, func);
    }

    public Function getFunc(String name) {
        return funcs.getOrDefault(name, null);
    }

    public void putClass(String name, classType cl, Position pos) {
        if (classes.containsKey(name))
            throw new internalError(pos, "multiple definition of " + name);
        classes.put(name, cl);
    }

    public classType getClass(String name) {
        return classes.getOrDefault(name, null);
    }
}
