package ASM.Entity;

public class memory extends Entity {
    public reg x;
    public imm offset;
    public int size;

    public memory(reg x, imm offset, int size) {
        this.x = x;
        this.size = size;
        this.offset = offset;
    }

    @Override
    public String toString() {
        return offset + "(" + x.toString() + ")";
    }
}
