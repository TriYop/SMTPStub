package org.yj.smtpstub.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


/**
 * SMTPStub
 * --------------------------------------------
 * Email object model defines email structure.
 *
 * @author TriYop
 * @since 1.0
 */
public final class EmailModel {

    /**
     * class logger
     */
    private static final Logger logger = LoggerFactory.getLogger(EmailModel.class);
    /**
     * date when message was received by server.
     * This may not be execution time due to asynchronous handling
     */
    private Date receivedDate;
    /**
     * message emitter
     */
    private String from;
    /**
     * message recipient
     */
    private String to;
    /**
     * message subject
     */
    private String subject;
    /**
     * message content body
     */
    private String emailStr;
    /**
     * message file path used for storing message.
     */
    private String filePath;


    /**
     * Instantiates a new Email model.
     */
    public EmailModel() {
        super();
    }

    /**
     * Gets received date.
     *
     * @return the received date
     */
    public Date getReceivedDate() {
        if (receivedDate == null) {
            return null;
        }
        return (Date) receivedDate.clone();
    }

    /**
     * Sets received date.
     *
     * @param receivedDate the received date
     */

    public void setReceivedDate(final Date receivedDate) {
        if (receivedDate == null) {
            this.receivedDate = null;
        } else {
            this.receivedDate = (Date) receivedDate.clone();
        }
    }

    /**
     * Gets from.
     *
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * Sets from.
     *
     * @param from the from
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Gets to.
     *
     * @return the to
     */
    public String getTo() {
        return to;
    }

    /**
     * Sets to.
     *
     * @param to the to
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * Gets subject.
     *
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets subject.
     *
     * @param subject the subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Gets email str.
     *
     * @return the email str
     */
    public String getEmailStr() {
        return emailStr;
    }

    /**
     * Sets email str.
     *
     * @param emailStr the email str
     */
    public void setEmailStr(String emailStr) {
        this.emailStr = emailStr;
    }

    /**
     * Gets file path.
     *
     * @return the file path
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Sets file path.
     *
     * @param filePath the file path
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return
     */
    public boolean hasEmptyField() {
        return null == this.emailStr || null == this.filePath || null == this.from || null == this.receivedDate || null == this.subject || null == this.to;
    }


}
