package ASM.Entity;

public class memory extends Entity {
    public reg x;
    public Entity offset;
    public int size;

    public memory(reg x, Entity offset, int size) {
        this.x = x;
        this.size = size;
        this.offset = offset;
    }

    @Override
    public String toString() {
        return offset + "(" + x.toString() + ")";
    }
}
