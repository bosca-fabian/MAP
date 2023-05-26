package Domain.Validators;

/**
 * Custom exception for entities that already exist
 */
public class EntityAlreadyExistsException extends RuntimeException{
    /**
     * constructor for alreadyexistsexception, no parameters
     */
    public EntityAlreadyExistsException() {
    }

    /**
     * constructor for alreadyexistsexception, message given as parameter
     * @param message
     */
    public EntityAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * constructor for alreadyexistsexception, parameters the message and the cause
     * @param message the message of the exception
     * @param cause the cause of the exception
     */

    public EntityAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * constructor for alreadyexistsexception, throwable cause
     * @param cause the cause of the exception
     */

    public EntityAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    /**
     * constructor for alreadyexistsexception, all parameters
     * @param message message of the exception
     * @param cause cause of the exception
     * @param enableSuppression whether to enable supression of the exception
     * @param writableStackTrace habar n-am ce e asta
     */

    public EntityAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
