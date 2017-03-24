package org.yj.smtpstub.exception;

/**
 * SMTPStub
 * --------------------------------------------
 *
 * @author TriYop
 * @since 1.0
 */
public class IncompleteEmailException extends Exception {
    public IncompleteEmailException() {
        super("Email message is not well filled and cannot be used as is.");
    }
}
