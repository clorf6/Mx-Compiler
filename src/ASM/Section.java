package ASM;

import java.util.ArrayList;

public class Section {
    public String name;
    public ArrayList<String> globl;
    public ArrayList<Function> func;
    public ArrayList<Word> word;
    public ArrayList<Asciz> str;

    public Section(String name) {
        this.name = name;
        globl = new ArrayList<>();
        func = new ArrayList<>();
        word = new ArrayList<>();
        str = new ArrayList<>();
    }

    public void merge() {
        func.forEach(Function::merge);
    }

    public String toString() {
        StringBuilder ret = new StringBuilder(".section " + name + "\n");
        for (String val : globl) {
            ret.append(".globl ").append(val).append("\n");
        }
        for (Function fc : func) ret.append(fc.toString()).append("\n");
        for (Word wd : word) {
            ret.append(".globl ").append(wd.name).append("\n");
            ret.append(wd).append("\n");
        }
        for (Asciz s : str) {
            ret.append(".globl ").append(s.name).append("\n");
            ret.append(s).append("\n");
        }
        return ret.toString();
    }

    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }
}
