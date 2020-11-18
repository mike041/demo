package exception;

public class DataNoFoundException extends Exception {
    String message;

    public DataNoFoundException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
