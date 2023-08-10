package IR.Instruction;

import IR.IRVisitor;
import IR.Object.Object;
import java.util.ArrayList;

public class call extends Instruction {

    Object res;
    String name;
    ArrayList<Object> args;

    public call(Object res, String name, ArrayList<Object> args) {
        this.res = res;
        this.name = name;
        this.args = args;
    }

    public call(String name, ArrayList<Object> args) {
        this.res = null;
        this.name = name;
        this.args = args;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        StringBuilder ret;
        if (res == null) {
            ret = new StringBuilder("call void @" + name + "(");
        } else {
            ret = new StringBuilder(res.getText() + " = call " + res.type.toString()
                    + " @" + name + "(");
        }
        for (Object arg : args) {
            ret.append(arg.toString());
        }
        ret.append(")");
        return ret.toString();
    }
}
