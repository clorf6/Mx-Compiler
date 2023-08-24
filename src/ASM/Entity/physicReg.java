package ASM.Entity;

public class physicReg extends reg {
    public physicReg(String name, int size) {
        super(name, size);
    }

    @Override
    public String toString() {
        return name;
    }
}
