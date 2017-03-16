package org.yj.smtpstub.exception;

import java.net.UnknownHostException;

/**
 * SMTPStub
 * --------------------------------------------
 * Thrown if the host name is invalid while trying to start the smtp.
 *
 * @author TriYop
 * @since 1.0
 */
public class InvalidHostException extends Exception {

    public InvalidHostException(UnknownHostException e, String host) {
        super("Host name could not be resolved as a valid address: " + host, e);
    }

}
