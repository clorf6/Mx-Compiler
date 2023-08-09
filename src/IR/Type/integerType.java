package IR.Type;

public class integerType extends Type {
    public int bit;

    public integerType(int bit) {
        this.bit = bit;
    }

    @Override
    public int size() {
        return (bit - 1) / 8 + 1;
    }

    @Override
    public String toString() {
        return "i" + bit;
    }
}
