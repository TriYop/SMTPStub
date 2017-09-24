package org.yj.smtpstub.service.smtp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subethamail.smtp.helper.SimpleMessageListenerAdapter;
import org.subethamail.smtp.server.SMTPServer;
import org.yj.smtpstub.exception.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * SMTPStub
 * --------------------------------------------
 * Instanciates and Starts the SMTP service.
 *
 * @author TriYop
 * @since 1.0
 */
public final class SMTPServerFactory {
    /**
     * Logs events into a dedicated stream
     */
    private static final Logger logger = LoggerFactory.getLogger(SMTPServerFactory.class);

    /**
     * default non accessible constructor for this final class
     */
    private SMTPServerFactory() {
    }


    /**
     * Starts the smtp on the given port and address
     *
     * @param port    the SMTP port to be opened.
     * @param address the address to bind to. null means bind to all.
     * @throws PortException            when the port can't be opened.
     * @throws IllegalArgumentException when port is out of range.
     */
    public static final SMTPServer getRunningServer(int port, String address) throws NetworkException {
        if (0 >= port || port >= 65536) {
            throw new InvalidPortException(null, port);
        }
        if (null == address || "".equals(address)) {
            throw new InvalidHostException(null, address);
        }

        try {
            final InetAddress bindAddress = InetAddress.getByName(address);
            final SMTPListener smtpListener = new SMTPListener();
            final SMTPServer smtpServer = new SMTPServer(new SimpleMessageListenerAdapter(smtpListener), new SMTPAuthHandlerFactory());

            smtpServer.setBindAddress(bindAddress);
            smtpServer.setHideTLS(true);
            smtpServer.setPort(port);
            smtpServer.start();
            return smtpServer;
        } catch (UnknownHostException exception) {
            logger.error("The provided hostname '{}' cannot be resolved as an IP address.", address, exception);
            throw new InvalidHostException(exception, address);

        } catch (RuntimeException exception) {
            logger.error("Error '{}' encountered while opening port {}.", exception.getMessage(), port, exception);
            throw new PortBindException(exception, port);
        }

    }

    /**
     * @param port
     * @param address
     * @return
     */
    public static final SMTPServer getRunningTLSServer(int port, String address) throws PortException, InvalidHostException {
        if (0 >= port || port >= 65536) {
            throw new InvalidPortException(null, port);
        }
        if (null == address || "".equals(address)) {
            throw new InvalidHostException(null, address);
        }

        try {
            final InetAddress bindAddress = InetAddress.getByName(address);
            final SMTPListener smtpListener = new SMTPListener();
            final SMTPServer smtpServer = new SMTPServer(new SimpleMessageListenerAdapter(smtpListener), new SMTPAuthHandlerFactory());

            smtpServer.setBindAddress(bindAddress);
            smtpServer.setHideTLS(false);
            smtpServer.setRequireTLS(true);
            smtpServer.setPort(port);
            smtpServer.start();
            logger.info("SMTP Server started, listening on port {}", port);
            return smtpServer;
        } catch (UnknownHostException exception) {
            logger.error("The provided hostname '{}' cannot be resolved as an IP address.", address, exception);
            throw new InvalidHostException(exception, address);

        } catch (RuntimeException exception) {
            logger.error("Error '{}' encountered while opening port {}.", exception.getMessage(), port, exception);
            throw new PortBindException(exception, port);

        }
    }

}
