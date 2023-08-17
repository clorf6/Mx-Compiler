package Utils.Error;
import Utils.Position;

public abstract class Error extends RuntimeException {
    private Position pos;
    private String message;

    public Error(String msg, Position pos) {
        this.pos = pos;
        if (pos == null) this.pos = new Position(0, 0);
        this.message = msg;
    }

    public String toString() {
        return message + ": " + pos.toString();
    }
}
