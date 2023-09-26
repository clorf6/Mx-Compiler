package ASM.Instruction;

import ASM.ASMVisitor;

public abstract class Instruction {
    public abstract String toString();
    public abstract void accept(ASMVisitor visitor);
}
