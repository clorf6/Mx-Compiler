package ASM.Entity;

public class physicReg extends reg {

    public boolean free = true;
    public physicReg(String name, int size) {
        super(name, size);
    }

    @Override
    public String toString() {
        return name;
    }
}
