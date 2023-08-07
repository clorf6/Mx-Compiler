package Utils.Error;
import Utils.Position;

public class semanticError extends Error {

    public semanticError(Position pos, String msg) {
        super("Semantic Error: " + msg, pos);
    }

}
