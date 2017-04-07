package org.yj.smtpstub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subethamail.smtp.server.SMTPServer;
import org.yj.smtpstub.configuration.Configuration;
import org.yj.smtpstub.processor.EmailProcessor;
import org.yj.smtpstub.service.smtp.SMTPServerFactory;
import org.yj.smtpstub.storage.FSMailStore;
import org.yj.smtpstub.storage.MailStoreFactory;

import javax.naming.ConfigurationException;

/**
 * SMTPStub
 * --------------------------------------------
 * Entry point of the SMTP Stubbed server
 *
 * @author TriYop
 * @since 1.0
 */
final class SMTPStub {
    private static final Logger logger = LoggerFactory.getLogger(SMTPStub.class);

    public static final String DEFAULT_STORAGE_ENGINE = FSMailStore.class.getCanonicalName();
    public static final String DEFAULT_BIND_ADDRESS = "0.0.0.0";
    public static final int DEFAULT_LISTEN_PORT = 25;

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
            String storeClassNmae= Configuration.get("emails.storage.engine", DEFAULT_STORAGE_ENGINE);

            EmailProcessor.setStore(MailStoreFactory.getMailStore(storeClassNmae));
            server = SMTPServerFactory.getRunningServer(
                    Configuration.getInt("smtp.secure.port", DEFAULT_LISTEN_PORT),
                    Configuration.get("smtp.bind.address", DEFAULT_BIND_ADDRESS));
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
