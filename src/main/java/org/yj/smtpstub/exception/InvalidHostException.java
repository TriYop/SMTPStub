package org.yj.smtpstub.exception;

import java.net.UnknownHostException;

/**
 * Thrown if the host name is invalid while trying to start the smtp.
 * <p>
 * This is a wrapper for UnknownHostException
 * </p>
 *
 * @author TriYop
 * @since 1.0
 */
public class InvalidHostException extends Exception {

    public InvalidHostException(UnknownHostException e, String host) {
        super("Host name could not be resolved as a valid address: " + host, e);
    }

}
