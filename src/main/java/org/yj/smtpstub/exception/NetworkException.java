package org.yj.smtpstub.exception;

/**
 * SMTPStub
 * --------------------------------------------
 * shared parent for network based exceptions thrown by SMTP handling classes.
 *
 * @author TriYop
 */
abstract public class NetworkException extends Exception {
    /**
     *
     * @param message
     * @param throwable
     */
    public NetworkException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
