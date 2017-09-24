package org.yj.smtpstub.exception;

/**
 * SMTPStub
 * --------------------------------------------
 * <p>
 * Thrown if the TCP server socket cannot be bound while trying to start the smtp server.
 * It may already be used by another application or the user running the server
 * is not allowed to open port (reserved ports, system security policy, ...)
 *
 * @author TriYop
 * @since 1.0
 */
public final class PortBindException extends PortException {
    /**
     * Creates a new PortBindException
     *
     * @param parent parent exception that was caught
     * @param port   port number involved in this exeption
     */
    public PortBindException(Exception parent, int port) {
        super(parent, port);
    }
}
