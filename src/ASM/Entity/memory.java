package ASM.Entity;

public class memory extends Entity {
    public physicReg x;
    public Entity offset;
    public int size;

    public memory(physicReg x, Entity offset, int size) {
        this.x = x;
        this.size = size;
        this.offset = offset;
    }

    @Override
    public String toString() {
        return offset + "(" + x.toString() + ")";
    }
}
