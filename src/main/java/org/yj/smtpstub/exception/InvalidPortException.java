package org.yj.smtpstub.exception;

/**
 * SMTPStub
 * --------------------------------------------
 * This exception is thrown when requested port is not in the valid range
 *
 * @author TriYop
 * @since 1.0
 */
public final class InvalidPortException extends PortException {
    public InvalidPortException(Exception ex, int port) {
        super(ex, port);
    }
}
