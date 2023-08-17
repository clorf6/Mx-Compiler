package IR.Instruction;

import IR.*;
import Utils.Position;

public abstract class Instruction {

    public abstract void accept(IRVisitor visitor);
    public abstract String toString();
}
