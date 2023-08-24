package ASM.Entity;

public class virtualReg extends reg {
    public virtualReg(String name, int size) {
        super(name, size);
    }

    @Override
    public String toString() {
        return "%" + name;
    }
}
