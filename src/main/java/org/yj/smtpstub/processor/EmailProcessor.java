package org.yj.smtpstub.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yj.smtpstub.exception.IncompleteEmailException;
import org.yj.smtpstub.model.EmailModel;
import org.yj.smtpstub.storage.MailStore;

import javax.annotation.Nonnull;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SMTPStub
 * --------------------------------------------
 * Class that processes email related actions.
 *
 * @author TriYop
 * @since 1.0
 */
public final class EmailProcessor {

    /**
     * logs events into a dedicated stream
     */
    private static final Logger logger = LoggerFactory.getLogger(EmailProcessor.class);
    /**
     * Subject  matching pattern
     */
    private static final Pattern SUBJECT_PATTERN = Pattern.compile("^Subject: (.*)$");
    /**
     * global Mail store object
     */
    private static MailStore store = null;

    /**
     *
     */
    private EmailProcessor() {
    }

    /**
     * Gets store.
     *
     * @return the store
     */
    public static MailStore getStore() {
        return store;
    }

    /**
     * Sets store.
     *
     * @param store the store
     */
    public static void setStore(MailStore store) {
        EmailProcessor.store = store;
    }

    /**
     * Process.
     *
     * @param from the from
     * @param dest   the dest
     * @param data the data
     */
    public static final void process(String from, String dest, InputStream data) throws IncompleteEmailException {
        if (from == null || dest == null || data == null) {
            throw new IncompleteEmailException();
        }

        EmailModel model = new EmailModel();
        model.setFrom(from);
        model.setFrom(from);
        model.setTo(dest);
        String mailContent = getStringFromStream(data);
        model.setSubject(parseMessageSubject(mailContent));
        model.setEmailStr(mailContent);
        model.setReceivedDate(new Date());

        if (store != null) {
            try {
                store.save(model);
            } catch (IncompleteEmailException exc) {
                logger.error("email was incomplete.  ", exc);
            }

        }
    }

    /**
     * @param inStream the input stream that will be transformed into a String
     * @return
     */
    static String getStringFromStream(@Nonnull InputStream inStream) {
        long prefixLines = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, Charset.forName("UTF8")));
        StringBuilder sb = new StringBuilder();

        String line;
        long lineNb = 0;
        try {
            while ((line = reader.readLine()) != null) {
                lineNb++;
                if (lineNb > prefixLines) {
                    sb.append(line).append(System.lineSeparator());
                }
            }
        } catch (IOException ioe) {
            logger.error("", ioe);
        }
        return sb.toString();
    }

    /**
     * Gets the subject from the email data passed in parameters.
     *
     * @param data a string representing the email content.
     * @return the subject of the email, or an empty subject if not found.
     */
    static String parseMessageSubject(@Nonnull String data) {
        try {
            BufferedReader reader = new BufferedReader(new StringReader(data));

            String line;
            while ((line = reader.readLine()) != null) {
                if ("".equals(line)) {
                    break;
                }
                Matcher matcher = SUBJECT_PATTERN.matcher(line);
                if (matcher.matches()) {
                    return matcher.group(1);
                }
            }
        } catch (IOException ioe) {
            logger.error("", ioe);
        }
        return "";
    }

}
