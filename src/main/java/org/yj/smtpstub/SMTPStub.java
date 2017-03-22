package org.yj.smtpstub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subethamail.smtp.server.SMTPServer;
import org.yj.smtpstub.configuration.Configuration;
import org.yj.smtpstub.processor.EmailProcessor;
import org.yj.smtpstub.service.smtp.SMTPServerFactory;
import org.yj.smtpstub.storage.MailStoreFactory;

/**
 * SMTPStub
 * --------------------------------------------
 * Entry point of the SMTP Stubbed server
 *
 * @author TriYop
 * @since 1.0
 */
public final class SMTPStub {
    private static final Logger logger = LoggerFactory.getLogger(SMTPStub.class);

    private SMTPStub() {
        throw new UnsupportedOperationException();
    }

    /**
     * Launches SMTPStub server
     *
     * @param args a list of arguments that are not in use.
     */
    public static void main(final String[] args) {
        SMTPServer server = null;
        try {
            EmailProcessor.setStore(MailStoreFactory.getMailStore(Configuration.get("emails.storage.engine")));
            server = SMTPServerFactory.getRunningServer(
                    Configuration.getInt("smtp.secure.port"),
                    Configuration.get("smtp.bind.address"));
        } catch (NumberFormatException e) {
            logger.error("Error: Invalid port number", e);
        } catch (Exception e) {
            logger.error("Failed to auto-start smtp in background", e);
        } finally {
            if (null != server && server.isRunning()) {
                server.stop();
            }
        }


    }
}
