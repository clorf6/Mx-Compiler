package ASM;

public class Asciz {
    String name;
    String val;

    public Asciz(String name, String val) {
        this.name = name;
        this.val = val;
    }

    public String toString() {
        return name + ":\n\t.asciz " + val;
    }
}
