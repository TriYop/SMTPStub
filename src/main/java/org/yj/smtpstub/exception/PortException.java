package org.yj.smtpstub.exception;

/**
 * SMTPStub
 * --------------------------------------------
 * Abstract class to simplify creation of exceptions due to a SMTP portnumber error.
 *
 * @author TriYop
 * @since 1.0
 */
public abstract class PortException extends NetworkException {
    /**
     * expected TCP portnumber involved in the exception
     */
    private final transient int portnumber;

    /**
     * Copies the stack trace of the exception passed in parameter, and sets the portnumber which caused the exception.
     *
     * @param parent the exception we need to copy the stack trace from.
     * @param port   the selected portnumber which was the cause of the exception.
     */
    public PortException(Exception parent, int port) {
        super("Port " + port + " could not be opened.", parent);
        if (parent != null) {
            setStackTrace(parent.getStackTrace());
        }
        this.portnumber = port;
    }

    /**
     * returns message based on portnumber number
     *
     * @return a String containing te error message that may be logged.
     */
    @Override
    public String getMessage() {
        return "Port " + portnumber + " could not be opened.";
    }
}
