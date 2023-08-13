package Utils.Scope;

import Utils.Position;
import Utils.Error.*;
import AST.Type.*;
import IR.Entity.*;

import java.util.HashMap;

public class Scope {
    public HashMap<String, Type> members;

    public HashMap<String, varEntity> vars;
    public Scope fatherScope;
    public boolean inloop;
    public int dep;

    public Scope(Scope fatherScope) {
        members = new HashMap<>();
        vars = new HashMap<>();
        this.fatherScope = fatherScope;
        if (fatherScope != null) {
            this.dep = fatherScope.dep + 1;
            this.inloop = fatherScope.inloop;
        }
    }

    public void putType(String name, Type type, Position pos) {
        if (members.containsKey(name)) {
            throw new semanticError(pos, "Semantic Error: variable redefine");
        }
        members.put(name, type);
    }

    public Type getType(String name, boolean recall) {
        if (members.containsKey(name)) return members.get(name);
        else if (fatherScope != null && recall)
            return fatherScope.getType(name, true);
        else return null;
    }

    public void putVar(String name, varEntity var, Position pos) {
        if (vars.containsKey(name)) {
            throw new internalError(pos, "Internal Error: variable redefine");
        }
        vars.put(name, var);
    }

    public varEntity getVar(String name, boolean recall) {
        if (vars.containsKey(name)) return vars.get(name);
        else if (fatherScope != null && recall) {
            return fatherScope.getVar(name, true);
        } else return null;
    }
}
