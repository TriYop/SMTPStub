package org.yj.smtpstub.exception;

/**
 * SMTPStub
 * --------------------------------------------
 * Exception thrown when store type is not valid.
 *
 * @author TriYop
 * @since 1.0
 */
public class InvalidStoreException extends Exception {
    /**
     * Creates a new InvalidStoreException
     *
     * @param parent the parent exception that was caught
     */
    public InvalidStoreException(Exception parent) {
        super(parent.getMessage(), parent);
    }
}
