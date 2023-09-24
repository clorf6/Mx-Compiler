package ASM;

public class Word {
    public boolean type;
    public String name;
    public String val;

    public Word(String name, String val, boolean type) {
        this.type = type;
        this.name = name;
        this.val = val;
    }

    public String toString() {
        if (this.type) return name + ":\n\t.byte " + val;
        else return name + ":\n\t.word " + val;
    }
}
