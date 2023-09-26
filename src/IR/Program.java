package IR;

import java.util.ArrayList;
import IR.Type.*;

public class Program {
    public Global globalInsts;
    public ArrayList<Function> funcs;

    public Program() {
        funcs = new ArrayList<>();
        funcs.add(new Function("_global_init", new voidType(), new ArrayList<>()));
        funcs.get(0).block.remove(1);
        globalInsts = new Global();
    }

    public void merge() {
        for (Function func : funcs) {
            func.merge();
        }
    }

    public String toString() {
        StringBuilder ret = new StringBuilder(globalInsts.toString());
        for (Function func : funcs) {
            ret.append(func.toString()).append("\n");
        }
        return ret.toString();
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
