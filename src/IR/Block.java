package IR;

import Utils.Error.internalError;
import IR.Type.*;
import IR.Instruction.*;
import Utils.Position;

import java.util.LinkedList;
import java.util.Objects;

public class Block {
    public labelType name;
    public LinkedList<Instruction> inst;
    public Instruction terminal;

    public Block(String name) {
        this.name = new labelType(name);
        inst = new LinkedList<>();
        terminal = null;
    }

    public void add(Instruction ins) {
        if (terminal != null) return ;
        inst.add(ins);
        if (ins instanceof br || ins instanceof ret) {
            terminal = ins;
        }
    }

    public String toString() {
        StringBuilder ret = new StringBuilder(name.name + ":\n");
        for (Instruction ins : inst) {
            ret.append("\t").append(ins.toString()).append("\n");
        }
        ret.append("\n");
        return ret.toString();
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
