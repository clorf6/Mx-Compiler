package ASM;
import ASM.Entity.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Program {

    public physicReg zero, ra, sp, gp, tp;
    public ArrayList<physicReg> t, s, a;
    public Section text;
    public Section data;
    public Section rodata;

    public Program() {
        text = new Section("text");
        data = new Section("data");
        rodata = new Section("rodata");
        zero = new physicReg("zero", 4);
        ra = new physicReg("ra", 4);
        sp = new physicReg("sp", 4);
        gp = new physicReg("gp", 4);
        tp = new physicReg("tp", 4);
        t = new ArrayList<>();
        s = new ArrayList<>();
        a = new ArrayList<>();
        for (int i = 0; i <= 6; i++) t.add(new physicReg("t" + i, 4));
        for (int i = 0; i <= 11; i++) s.add(new physicReg("s" + i, 4));
        for (int i = 0; i <= 7; i++) a.add(new physicReg("a" + i, 4));
    }

    public physicReg t(int i) {
        return t.get(i);
    }

    public physicReg s(int i) {
        return s.get(i);
    }

    public physicReg a(int i) {
        return a.get(i);
    }

    public void merge() {
        text.merge();
    }

    public String toString() {
        return text.toString() + "\n" + data.toString() + "\n" + rodata.toString();
    }

    public void accept(ASMVisitor visitor) {
        visitor.visit(this);
    }
}
