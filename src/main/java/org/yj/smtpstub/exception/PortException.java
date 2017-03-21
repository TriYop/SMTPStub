package org.yj.smtpstub.exception;

/**
 * SMTPStub
 * --------------------------------------------
 * Abstract class to simplify creation of exceptions due to a SMTP port error.
 *
 * @author TriYop
 * @since 1.0
 */
public abstract class PortException extends Exception {

    private final int port;

    /**
     * Copies the stack trace of the exception passed in parameter, and sets the port which caused the exception.
     *
     * @param e    the exception we need to copy the stack trace from.
     * @param port the selected port which was the cause of the exception.
     */
    public PortException(Exception e, int port) {
        if (e!=null) {
            setStackTrace(e.getStackTrace());
        }
        this.port = port;
    }

    /**
     * Returns the port entered by the user.
     * <p>
     * Useful to know why the SMTP smtp could not start.
     * </p>
     *
     * @return the port which caused the exception.
     */
    public int getPort() {
        return port;
    }
}
