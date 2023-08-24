package ASM.Instruction;

public class call extends Instruction {
    String label;

    public call(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return String.format("%-8s", "call") + label;
    }
}
