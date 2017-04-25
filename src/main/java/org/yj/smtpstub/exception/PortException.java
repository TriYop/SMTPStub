package org.yj.smtpstub.exception;

/**
 * SMTPStub
 * --------------------------------------------
 * Abstract class to simplify creation of exceptions due to a SMTP port error.
 *
 * @author TriYop
 * @since 1.0
 */
public abstract class PortException extends NetworkException {
    /**
     * expected TCP port involved in the exception
     */
    private final transient int port;

    /**
     * Copies the stack trace of the exception passed in parameter, and sets the port which caused the exception.
     *
     * @param e    the exception we need to copy the stack trace from.
     * @param port the selected port which was the cause of the exception.
     */
    public PortException(Exception e, int port) {
        super();
        if (e!=null) {
            setStackTrace(e.getStackTrace());
        }
        this.port = port;
    }

    /**
     * returns message based on port number
     * @return a String containing te error message that may be logged.
     */
    @Override
    public String getMessage() {
        return "Port " + port + " could not be opened.";
    }
}
