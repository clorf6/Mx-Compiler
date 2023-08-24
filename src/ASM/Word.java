package ASM;

public class Word {
    public String name;
    public int val;

    public Word(String name, int val) {
        this.name = name;
        this.val = val;
    }

    public String toString() {
        return name + ":\n\t.word " + val;
    }
}
