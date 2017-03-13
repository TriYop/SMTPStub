package org.yj.smtpstub.exception;

/**
 * Thrown if the TCP server socket cannot be bound while trying to start the smtp server.
 * <p>
 * It may already be used by another application or the user running the server
 * is not allowed to open port (reserved ports, system security policy, ...)
 *
 * @author TriYop
 * @since 1.0
 */
public final class PortBindException extends PortException {

    public PortBindException(Exception e, int port) {
        super(e, port);
    }
}
