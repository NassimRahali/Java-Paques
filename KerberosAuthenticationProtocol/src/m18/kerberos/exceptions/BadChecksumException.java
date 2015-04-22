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
public class BadChecksumException extends Exception {

    public BadChecksumException() {
    }

    public BadChecksumException(String message) {
        super(message);
    }

    public BadChecksumException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
