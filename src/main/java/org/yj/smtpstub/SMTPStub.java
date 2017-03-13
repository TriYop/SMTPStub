package org.yj.smtpstub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subethamail.smtp.server.SMTPServer;
import org.yj.smtpstub.configuration.Configuration;
import org.yj.smtpstub.processor.EmailProcessor;
import org.yj.smtpstub.service.smtp.SMTPServerFactory;
import org.yj.smtpstub.storage.MailStoreFactory;

/**
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
     * Checks command line arguments, sets some specific properties, and runs the main window.
     * <p>
     * Before opening the main window, this method will:
     * </p>
     * <ul>
     * <li>check command line arguments, and possibly display an error dialog,</li>
     * <li>set a default uncaught exception handler to intercept every uncaught exception;</li>
     * <li>use a custom icon in the Mac Dock;</li>
     * <li>set a property for Mac OS X to take the menu bar off the JFrame;</li>
     * <li>set a property for Mac OS X to set the name of the application menu item;</li>
     * <li>turn off the bold font in all components for swing default theme;</li>
     * <li>use the platform look and feel.</li>
     * </ul>
     *
     * @param args a list of command line parameters.
     */
    public static void main(final String[] args) {

        try {
            EmailProcessor.setStore(MailStoreFactory.getMailStore(Configuration.get("emails.storage.engine")));
            SMTPServer server = SMTPServerFactory.getRunningServer(
                    Configuration.getInt("smtp.secure.port"),
                    Configuration.get("smtp.bind.address"));
        } catch (NumberFormatException e) {
            logger.error("Error: Invalid port number", e);
        } catch (Exception e) {
            logger.error("Failed to auto-start smtp in background", e);
        }

    }
}
