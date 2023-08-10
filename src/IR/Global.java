package IR;

import IR.Instruction.Instruction;

import java.util.ArrayList;

public class Global {
    ArrayList<Instruction> insts;

    public Global() {
        insts = new ArrayList<>();
    }

    public void add(Instruction inst) {
        insts.add(inst);
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
