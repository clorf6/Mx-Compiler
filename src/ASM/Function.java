package ASM;

import java.util.ArrayList;

public class Function {
    public String name;
    public int size, callSize;
    public Block retBlock;
    public ArrayList<Block> block;
    public ArrayList<Block> RPO;
    public Function(String name) {
        this.name = name;
        size = callSize = 0;
        block = new ArrayList<>();
        RPO = new ArrayList<>();
    }

    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (Block bl : block) ret.append(bl.toString()).append("\n");
        return ret.toString();
    }

    public void merge() {
        block.forEach(Block::merge);
    }

    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }
}
