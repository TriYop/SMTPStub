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
    /**
     * Creates a new InvalidPortException
     *
     * @param parent   parent exception that was caught
     * @param port the port number involved in the exception
     */
    public InvalidPortException(Exception parent, int port) {
        super(parent, port);
    }
}
