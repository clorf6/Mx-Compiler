package IR;

import Utils.Error.internalError;
import IR.Type.*;
import IR.Instruction.*;
import Utils.Position;

import java.util.*;

public class Block {
    public labelType name;
    public LinkedList<Instruction> inst;
    public ArrayList<phi> phiInst;
    public Instruction terminal;

    public ArrayList<Block> pre;
    public ArrayList<Block> suc;
    public int dfn;
    public Block anc, idom, semi, fa, minn;
    public ArrayList<Block> tr;
    public HashSet<Block> DomFrontier;

    public Block(String name) {
        this.name = new labelType(name);
        this.pre = new ArrayList<>();
        this.suc = new ArrayList<>();
        this.phiInst = new ArrayList<>();
        tr = new ArrayList<>();
        DomFrontier = new HashSet<>();
        dfn = 0;
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

    public void addPhi(phi ins) {
        phiInst.add(ins);
    }

    public String toString() {
        StringBuilder ret = new StringBuilder(name.name + ":\n");
        if (!phiInst.isEmpty()) {
            for (phi ins : phiInst) ret.append("\t").append(ins.toString()).append("\n");
        }
        for (Instruction ins : inst) ret.append("\t").append(ins.toString()).append("\n");
        ret.append("\n");
        return ret.toString();
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
