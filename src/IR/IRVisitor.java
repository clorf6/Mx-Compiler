package IR;

import IR.Instruction.*;

public interface IRVisitor {
    void visit(binary it);

    void visit(br it);

    void visit(ret it);

    void visit(alloca it);

    void visit(load it);

    void visit(store it);

    void visit(global it);

    void visit(declare it);

    void visit(classdef it);

    void visit(getelementptr it);

    void visit(icmp it);

    void visit(call it);

    void visit(phi it);

    void visit(Block it);

    void visit(Function it);

    void visit(Global it);

    void visit(Program it);
}
