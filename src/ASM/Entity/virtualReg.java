package ASM.Entity;

import java.util.Comparator;

public class virtualReg extends reg {

    public int beg = Integer.MAX_VALUE, end = -1;
    public virtualReg(String name, int size) {
        super(name, size);
    }
    public static class regComparator implements Comparator<virtualReg> {
        @Override
        public int compare(virtualReg lhs, virtualReg rhs) {
            if (lhs.beg == rhs.beg) {
                return Integer.compare(lhs.end, rhs.end);
            }
            return Integer.compare(lhs.beg, rhs.beg);
        }
    }
    @Override
    public String toString() {
        return "%" + name;
    }
}
