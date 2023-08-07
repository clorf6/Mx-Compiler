package Utils.Error;
import Utils.Position;

public class syntaxError extends Error {

    public syntaxError(Position pos, String msg) {
        super("SyntaxError: " + msg, pos);
    }

}
