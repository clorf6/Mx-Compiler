package IR.Instruction;

import IR.IRVisitor;
import IR.Entity.*;
import IR.Type.labelType;
import Utils.Error.internalError;
import Utils.Position;

import java.util.ArrayList;

public class phi extends Instruction {
    public Entity res;
    public localVarEntity addr;
    public ArrayList<Entity> val;
    public ArrayList<labelType> label;

    public phi(Entity res, ArrayList<Entity> val, ArrayList<labelType> label, Position pos) {
        this.res = res;
        this.val = val;
        this.label = label;
    }

    public phi(Entity res, localVarEntity addr, ArrayList<Entity> val, ArrayList<labelType> label, Position pos) {
        this(res, val, label, pos);
        this.addr = addr;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        if (val.isEmpty()) System.out.println(res.toString());
        StringBuilder ret = new StringBuilder(res.getText() + " = phi " + res.type.toString()
                + " [ " + val.get(0).getText() + ", " + label.get(0).getText() + " ]");
        for (int i = 1; i < val.size(); i++) {
            ret.append(", [ ").append(val.get(i).getText()).append(", ").append(label.get(i).getText()).append(" ]");
        }
        return ret.toString();
    }

}
