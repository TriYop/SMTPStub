package org.yj.smtpstub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subethamail.smtp.server.SMTPServer;
import org.yj.smtpstub.configuration.Configuration;
import org.yj.smtpstub.configuration.ConfigurationLoader;
import org.yj.smtpstub.configuration.PropertiesConfigurationLoader;
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
final class SMTPStub {

    /**
     * default IPv4 bind address
     */
    public static final String DEFAULT_BIND_ADDRESS = "0.0.0.0";
    /**
     * default TCP port used for listening
     */
    public static final int DEFAULT_LISTEN_PORT = 25;
    /**
     * logs events in a dedicated stream
     */
    private static final Logger logger = LoggerFactory.getLogger(SMTPStub.class);

    /**
     * default private constructor that should never be used
     */
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
        ConfigurationLoader confLoader= new PropertiesConfigurationLoader("/etc/smtpstub.conf");
        Configuration config = Configuration.getInstance(confLoader);
        try {
            EmailProcessor.setStore(MailStoreFactory.getMailStore());
            server = SMTPServerFactory.getRunningServer(
                    config.getIntValue("smtp.port.default", DEFAULT_LISTEN_PORT),
                    config.getStringValue("smtp.bind.address", DEFAULT_BIND_ADDRESS));
            do {
                Thread.sleep(5000);
            } while (server.isRunning());
        } catch (NumberFormatException e) {
            logger.error("Error: Invalid port number", e);
        } catch (InterruptedException e) {
            logger.info("Shutting server down.");
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            logger.error("Failed to auto-start smtp in background", e);
        } finally {
            if (null != server && server.isRunning()) {
                server.stop();
            }
        }


    }
}
