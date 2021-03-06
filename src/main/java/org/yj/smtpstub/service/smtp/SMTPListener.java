package org.yj.smtpstub.service.smtp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subethamail.smtp.helper.SimpleMessageListener;
import org.yj.smtpstub.exception.IncompleteEmailException;
import org.yj.smtpstub.processor.EmailProcessor;

import java.io.IOException;
import java.io.InputStream;

/**
 * SMTPStub
 * --------------------------------------------
 * Listens to incoming emails and redirects them to the {@code EmailProcessor} object.
 *
 * @author TriYop
 * @since 1.0
 */
final class SMTPListener implements SimpleMessageListener {
    /**
     * Logs events into a dedicated stream
     */
    private static final Logger logger = LoggerFactory.getLogger(SMTPListener.class);

    /**
     * determins if messsage will be accepted for delivery or not
     *
     * @param from      the user who send the email.
     * @param recipient the recipient of the email.
     * @return always return {@code true}
     * @todo add some configuration based filtering based on source/target domains
     */
    public boolean accept(String from, String recipient) {
        return true;
    }

    /**
     * Receives emails and forwards them to the {@link EmailProcessor} object.
     */
    @Override
    public void deliver(String from, String recipient, InputStream data) throws IOException {
        try {
            EmailProcessor.process(from, recipient, data);
        } catch (IncompleteEmailException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
