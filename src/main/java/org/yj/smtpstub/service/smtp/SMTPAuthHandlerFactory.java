package org.yj.smtpstub.service.smtp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subethamail.smtp.AuthenticationHandler;
import org.subethamail.smtp.AuthenticationHandlerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * SMTPStub
 * --------------------------------------------
 * The factory interface for creating authentication handlers.
 * with / without TLS capabilities
 * @author TriYop
 * @since 1.0
 */
final class SMTPAuthHandlerFactory implements AuthenticationHandlerFactory {
    private static final Logger logger = LoggerFactory.getLogger(SMTPAuthHandler.class);
    private static final String LOGIN_MECHANISM = "LOGIN";

    /**
     *
     * @return
     */
    @Override
    public AuthenticationHandler create() {
        logger.debug( "Created new SMTP Authentication handler" );
        return new SMTPAuthHandler();
    }

    /**
     *
     * @return
     */
    @Override
    public List<String> getAuthenticationMechanisms() {
        List<String> result = new ArrayList<>();
        result.add(SMTPAuthHandlerFactory.LOGIN_MECHANISM);
        return result;
    }
}
