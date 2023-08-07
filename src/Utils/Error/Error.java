package Utils.Error;
import Utils.Position;

public abstract class Error extends RuntimeException {
    private Position pos;
    private String message;

    public Error(String msg, Position pos) {
        this.pos = pos;
        this.message = msg;
    }

    public String toString() {
        return message + ": " + pos.toString();
    }
}
