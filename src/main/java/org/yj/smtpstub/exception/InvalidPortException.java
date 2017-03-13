package org.yj.smtpstub.exception;

public final class InvalidPortException extends PortException {
    public InvalidPortException(Exception ex, int port) {
        super(ex, port);
    }
}
