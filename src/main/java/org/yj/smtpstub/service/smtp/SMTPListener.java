package org.yj.smtpstub.service.smtp;

import org.subethamail.smtp.helper.SimpleMessageListener;
import org.yj.smtpstub.processor.EmailProcessor;

import java.io.IOException;
import java.io.InputStream;

/**
 * Listens to incoming emails and redirects them to the {@code EmailProcessor} object.
 *
 * @author TriYop
 * @since 1.0
 */
public final class SMTPListener implements SimpleMessageListener {

    /**
     * determins if messsage will be accepted for delivery or not.
     * <p>
     * TODO: add some configuration based filtering based on source/target domains
     *
     * @param from      the user who send the email.
     * @param recipient the recipient of the email.
     * @return always return {@code true}
     */
    public boolean accept(String from, String recipient) {
        return true;
    }

    /**
     * Receives emails and forwards them to the {@link EmailProcessor} object.
     */
    @Override
    public void deliver(String from, String recipient, InputStream data) throws IOException {
        EmailProcessor.process(from, recipient, data);
    }
}
