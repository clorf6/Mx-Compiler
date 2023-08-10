package IR;

import java.util.ArrayList;

public class Program {
    Global globalInsts;
    ArrayList<Function> funcs;

    public Program() {}

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
