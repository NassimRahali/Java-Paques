/*
 * RAHALI Nassim
 * M18
 * 2014-2015
 */
package m18.kerberos.exceptions;

/**
 *
 * @author Nassim
 */
public class BadTimestampException extends Exception {

    public BadTimestampException() {
    }

    public BadTimestampException(String message) {
        super(message);
    }

    public BadTimestampException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
