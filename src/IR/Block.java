package IR;

import Utils.Error.internalError;
import IR.Type.*;
import IR.Instruction.*;

import java.util.LinkedList;

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
        if (terminal != null) {
            throw new internalError(null, "Block add error");
        }
        inst.add(ins);
        if (ins instanceof br || ins instanceof ret) {
            terminal = ins;
        }
    }

    public String toString() {
        StringBuilder ret = new StringBuilder(name.getText() + ":\n");
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
