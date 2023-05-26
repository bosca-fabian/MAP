package Domain.Validators;

public class WrongDateFormatException extends RuntimeException{
    public WrongDateFormatException() {
    }

    public WrongDateFormatException(String message) {
        super(message);
    }

    public WrongDateFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongDateFormatException(Throwable cause) {
        super(cause);
    }

    public WrongDateFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
