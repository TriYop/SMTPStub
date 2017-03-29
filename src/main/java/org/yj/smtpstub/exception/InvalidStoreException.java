package org.yj.smtpstub.exception;

/**
 * SMTPStub
 * --------------------------------------------
 * Exception thrown when store type is not valid.
 *
 * @author TriYop
 * @since  1.0
 */
public class InvalidStoreException extends Exception {
    public InvalidStoreException(Exception parent) {
        super(parent.getMessage(), parent);
    }
}
