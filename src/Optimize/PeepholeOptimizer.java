package Optimize;

import ASM.Instruction.*;
import ASM.Program;
import ASM.Entity.*;
import java.util.LinkedList;

public class PeepholeOptimizer {
    Program program;

    public PeepholeOptimizer(Program program) {
        this.program = program;
        run();
    }

    public void run() {
        for (var func : program.text.func) {
            for (var block : func.block) {
                LinkedList<Instruction> newInst = new LinkedList<>();
                int i = 0;
                while (i < block.inst.size()) {
                    var now = block.inst.get(i);
                    if (now instanceof mv mv1) {
                        if (mv1.rd == mv1.rs) {
                            i++;
                            continue;
                        } else if (i + 1 < block.inst.size() && block.inst.get(i + 1) instanceof mv mv2) {
                            if (mv1.rd == mv2.rs && mv1.rs == mv2.rd) {
                                newInst.add(now);
                                i += 2;
                                continue;
                            } else if (i + 2 < block.inst.size() && block.inst.get(i + 2) instanceof mv mv3) {
                                if (mv1.rd == mv2.rs && mv2.rd == mv3.rs && mv3.rd == mv1.rs) {
                                    newInst.add(now);
                                    newInst.add(mv2);
                                    i += 3;
                                    continue;
                                }
                            }
                        }
                    } else if (now instanceof load ld) {
                        if (i + 1 < block.inst.size() && block.inst.get(i + 1) instanceof store st) {
                            if (ld.rd == st.val && ld.ms == st.ms) {
                                newInst.add(now);
                                i += 2;
                                continue;
                            }
                        }
                    } else if (now instanceof store st) {
                        if (i + 1 < block.inst.size() && block.inst.get(i + 1) instanceof load ld) {
                            if (ld.rd == st.val && ld.ms == st.ms) {
                                newInst.add(now);
                                i += 2;
                                continue;
                            }
                        }
                    } else if (now instanceof binary bin) {
                        if (bin.isImm) {
                            if ((bin.op.equals("add") || bin.op.equals("sub")) && ((imm) bin.op2).val == 0) {
                                newInst.add(new mv((reg) bin.res, (reg) bin.op1));
                                i++;
                                continue;
                            } else if ((bin.op.equals("mul") || bin.op.equals("div")) && ((imm) bin.op2).val == 1) {
                                newInst.add(new mv((reg) bin.res, (reg) bin.op1));
                                i++;
                                continue;
                            } else if (bin.op.equals("mul") && ((imm) bin.op2).val == 0) {
                                newInst.add(new li((reg) bin.res, new imm(0)));
                                i++;
                                continue;
                            }
                            if (i + 1 < block.inst.size() && block.inst.get(i + 1) instanceof binary bin2 && bin2.isImm) {
                                if ((bin.op.equals("add") || bin.op.equals("sub")) && bin2.op.equals(bin.op) && bin.res == bin2.op1 && bin.res == bin2.res) {
                                    bin2.op1 = bin.op1;
                                    bin2.op2 = new imm(((imm) bin.op2).val + ((imm) bin2.op2).val);
                                    i++;
                                    continue;
                                } else if (bin.op.equals("mul") && bin2.op.equals(bin.op) && bin.res == bin2.op1 && bin.res == bin2.res) {
                                    bin2.op1 = bin.op1;
                                    bin2.op2 = new imm(((imm) bin.op2).val * ((imm) bin2.op2).val);
                                    i++;
                                    continue;
                                } else if (bin.op.equals("and") && bin2.op.equals(bin.op) && bin.res == bin2.op1 && bin.res == bin2.res) {
                                    bin2.op1 = bin.op1;
                                    bin2.op2 = new imm(((imm) bin.op2).val & ((imm) bin2.op2).val);
                                    i++;
                                    continue;
                                } else if (bin.op.equals("or") && bin2.op.equals(bin.op) && bin.res == bin2.op1 && bin.res == bin2.res) {
                                    bin2.op1 = bin.op1;
                                    bin2.op2 = new imm(((imm) bin.op2).val | ((imm) bin2.op2).val);
                                    i++;
                                    continue;
                                } else if (bin.op.equals("xor") && bin2.op.equals(bin.op) && bin.res == bin2.op1 && bin.res == bin2.res) {
                                    bin2.op1 = bin.op1;
                                    bin2.op2 = new imm(((imm) bin.op2).val ^ ((imm) bin2.op2).val);
                                    i++;
                                    continue;
                                }
                            }
                        }
                        if (bin.op.equals("add") && bin.op1 == bin.op2) {
                            newInst.add(new binary(bin.res, bin.op1, "slli", new imm(1)));
                            i++;
                            continue;
                        } else if (bin.op.equals("sub") && bin.op1 == bin.op2) {
                            newInst.add(new li((reg) bin.res, new imm(0)));
                            i++;
                            continue;
                        }
                    }
                    newInst.add(now);
                    i++;
                }
                block.inst = newInst;
            }
        }
    }
}
