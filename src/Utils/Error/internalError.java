package Utils.Error;
import Utils.Position;

public class internalError extends Error {

    public internalError(Position pos, String msg) {
        super("Internal Error:" + msg, pos);
    }

}
