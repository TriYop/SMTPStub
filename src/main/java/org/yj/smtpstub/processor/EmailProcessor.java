package org.yj.smtpstub.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yj.smtpstub.model.EmailModel;
import org.yj.smtpstub.storage.MailStore;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by TriYop on 01/03/2017.
 */
public final class EmailProcessor {

    private static final Logger logger = LoggerFactory.getLogger(EmailProcessor.class);
    private static final Pattern SUBJECT_PATTERN = Pattern.compile("^Subject: (.*)$");
    private static MailStore store = null;

    private EmailProcessor() {}

    public static MailStore getStore() {
        return store;
    }

    public static void setStore(MailStore store) {
        EmailProcessor.store = store;
    }

    public static final void process(String from, String to, InputStream data) {
        EmailModel model = new EmailModel();
        model.setFrom(from);
        model.setFrom(from);
        model.setTo(to);
        String mailContent = getStringFromStream(data);
        model.setSubject(parseMessageSubject(mailContent));
        model.setEmailStr(mailContent);
        model.setReceivedDate(new Date());

        if (store != null) {
            store.save(model);
        }
    }

    private static String getStringFromStream(InputStream is) {
        // final long prefixLines = 4; // Do not copy the first 4 lines (received part)
        final long prefixLines = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF8")));
        StringBuilder sb = new StringBuilder();

        String line;
        long lineNb = 0;
        try {
            while ((line = reader.readLine()) != null) {
                if (++lineNb > prefixLines) {
                    sb.append(line).append(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            logger.error("", e);
        }
        return sb.toString();
    }

    /**
     * Gets the subject from the email data passed in parameters.
     *
     * @param data a string representing the email content.
     * @return the subject of the email, or an empty subject if not found.
     */
    private static String parseMessageSubject(String data) {
        try {
            BufferedReader reader = new BufferedReader(new StringReader(data));

            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = SUBJECT_PATTERN.matcher(line);
                if (matcher.matches()) {
                    return matcher.group(1);
                }
            }
        } catch (IOException e) {
            logger.error("", e);
        }
        return "";
    }

}
