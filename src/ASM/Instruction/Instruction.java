package ASM.Instruction;

import ASM.ASMVisitor;

import java.util.HashSet;
import ASM.Entity.*;


public abstract class Instruction {

    public int pos = -1;
    public abstract String toString();
    public abstract void accept(ASMVisitor visitor);

    public abstract HashSet<virtualReg> getUse();
    public abstract HashSet<virtualReg> getDef();
    public abstract void updateUsed();
    public abstract void update();
}
