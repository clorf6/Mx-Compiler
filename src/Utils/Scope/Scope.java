package Utils.Scope;

import Utils.Position;
import Utils.Error.*;
import AST.Type.*;

import java.util.HashMap;

public class Scope {
    public HashMap<String, Type> members;
    public Scope fatherScope;
    public boolean inloop;
    public int dep;

    public Scope(Scope fatherScope) {
        members = new HashMap<>();
        this.fatherScope = fatherScope;
        if (fatherScope != null) {
            this.dep = fatherScope.dep + 1;
            this.inloop = fatherScope.inloop;
        }
    }

    public void put(String name, Type type, Position pos) {
        if (members.containsKey(name)) {
            throw new semanticError(pos, "Semantic Error: variable redefine");
        }
        members.put(name, type);
    }

    public boolean find(String name, boolean recall) {
        if (members.containsKey(name)) return true;
        else if (fatherScope != null && recall)
            return fatherScope.find(name, true);
        else return false;
    }

    public Type get(String name, boolean recall) {
        if (members.containsKey(name)) return members.get(name);
        else if (fatherScope != null && recall)
            return fatherScope.get(name, true);
        else return null;
    }
}
