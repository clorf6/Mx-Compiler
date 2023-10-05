package ASM;

import ASM.Instruction.*;

public interface ASMVisitor {
    void visit(Program program);
    void visit(Section section);

    void visit(Function function);
    void visit(Block block);

    void visit(binary inst);
    void visit(call inst);
    void visit(comp inst);
    void visit(j inst);
    void visit(br inst);
    void visit(load inst);
    void visit(store inst);
    void visit(mv inst);
    void visit(ret inst);
    void visit(li inst);
    void visit(la inst);
}
