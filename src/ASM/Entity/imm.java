package ASM.Entity;

public class imm extends Entity {
    public int val;
    public imm(int val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return Integer.toString(val);
    }
}
