package IR.Instruction;

import IR.*;

public abstract class Instruction {
    public abstract void accept(IRVisitor visitor);
    public abstract String toString();
}
