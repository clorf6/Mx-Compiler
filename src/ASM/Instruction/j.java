package ASM.Instruction;

public class j extends Instruction {
    public String label;

    public j(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return String.format("%-8s", "j") + label;
    }
}
