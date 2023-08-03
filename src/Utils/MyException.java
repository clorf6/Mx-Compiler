package Utils;
public class MyException extends RuntimeException {
    public MyException(position pos, String message) {
        super(pos.toString() + message);
    }

    public MyException(String message) {
        super(message);
    }
}