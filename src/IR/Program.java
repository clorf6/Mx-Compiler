package IR;

import java.util.ArrayList;
import IR.Type.*;

public class Program {
    public Global globalInsts;
    public ArrayList<Function> funcs;

    public Program() {
        funcs = new ArrayList<>();
        funcs.add(new Function("_global_init", new voidType(), new ArrayList<>()));
        globalInsts = new Global();
    }

    public String toString() {
        StringBuilder ret = new StringBuilder(globalInsts.toString());
        for (Function func : funcs) {
            ret.append(func.toString());
        }
        return ret.toString();
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
