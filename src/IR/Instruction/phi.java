package IR.Instruction;

import IR.IRVisitor;
import IR.Object.Object;
import IR.Type.labelType;
import Utils.Error.internalError;

import java.util.ArrayList;

public class phi extends Instruction {
    Object res;
    ArrayList<Object> val;
    ArrayList<labelType> label;

    public phi(Object res, ArrayList<Object> val, ArrayList<labelType> label) {
        this.res = res;
        this.val = val;
        this.label = label;
        if (val.size() != label.size()) {
            throw new internalError(null, "Phi instruction number wrong");
        }
        if (val.isEmpty()) {
            throw new internalError(null, "Phi instruction number wrong");
        }
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder(res.getText() + " = phi " + res.type.toString()
                + " [ " + val.get(0).getText() + ", " + label.get(0).getText() + " ]");
        for (int i = 1; i < val.size(); i++) {
            ret.append(", [ ").append(val.get(i).getText()).append(", ").append(label.get(i).getText()).append(" ]");
        }
        return ret.toString();
    }

}
